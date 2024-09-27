resource "google_service_account" "embro-ui" {
  account_id   = "embro-ui"
  display_name = "Embro UI"
}

resource "google_service_account" "embro-api" {
  account_id   = "embro-api"
  display_name = "Embro API"
}

resource "google_project_iam_member" "embro-api-cloudsql-client" {
  project = var.project_id
  role    = "roles/cloudsql.client"
  member  = format("serviceAccount:%s", google_service_account.embro-api.email)
}

resource "google_project_iam_member" "embro-api-cloudsql-instance" {
  project = var.project_id
  role    = "roles/cloudsql.instanceUser"
  member  = format("serviceAccount:%s", google_service_account.embro-api.email)
}

resource "google_sql_database_instance" "embro" {
  name             = "embro"
  database_version = "POSTGRES_15"
  region           = "europe-west1"
  deletion_protection = false

  settings {
    tier      = "db-f1-micro"
    disk_type = "PD_HDD"
    disk_size = 10

    database_flags {
      name  = "cloudsql.iam_authentication"
      value = "on"
    }
  }
}

resource "google_sql_database" "embro" {
  name     = "embro"
  instance = google_sql_database_instance.embro.name
}

resource "google_sql_user" "embro" {
  name     = trimsuffix(google_service_account.embro-api.email, ".gserviceaccount.com")
  instance = google_sql_database_instance.embro.name
  type     = "CLOUD_IAM_SERVICE_ACCOUNT"
}

data "google_artifact_registry_docker_image" "embro-api" {
  location      = "europe-west1"
  repository_id = "embro"
  image_name    = "embro-api:latest"
}

resource "google_cloud_run_v2_service" "embro-api" {
  name                = "embro-api"
  location            = "europe-west1"
  deletion_protection = false
  ingress             = "INGRESS_TRAFFIC_ALL"

  template {
    service_account = google_service_account.embro-api.email

    containers {
      image = data.google_artifact_registry_docker_image.embro-api.self_link

      ports {
        container_port = 8080
      }

      volume_mounts {
        name       = "cloudsql"
        mount_path = "/cloudsql"
      }

      env {
        name  = "SPRING_DATASOURCE_URL"
        value = "jdbc:postgresql:///${google_sql_database_instance.embro.name}?cloudSqlInstance=${google_sql_database_instance.embro.connection_name}&socketFactory=com.google.cloud.sql.postgres.SocketFactory&enableIamAuth=true"
      }
      env {
        name  = "SPRING_DATASOURCE_USERNAME"
        value = trimsuffix(google_service_account.embro-api.email, ".gserviceaccount.com")
      }
      env {
        name  = "SPRING_DATASOURCE_PASSWORD"
        value = "useless"
      }
      env {
        name = "APP_CORS_ALLOWED_ORIGINS"
        value = google_cloud_run_v2_service.embro-ui.uri
      }

      startup_probe {
        initial_delay_seconds = 10
        timeout_seconds       = 2
        period_seconds        = 3
        failure_threshold     = 5
        tcp_socket {
          port = 8080
        }
      }
    }

    volumes {
      name = "cloudsql"
      cloud_sql_instance {
        instances = [google_sql_database_instance.embro.connection_name]
      }
    }

    scaling {
      min_instance_count = 0
      max_instance_count = 1
    }
  }
}

resource "google_cloud_run_v2_service_iam_member" "embro-api" {
  name     = google_cloud_run_v2_service.embro-api.name
  location = google_cloud_run_v2_service.embro-api.location
  role     = "roles/run.invoker"
  member   = "allUsers"
}

data "google_artifact_registry_docker_image" "embro-ui" {
  location      = "europe-west1"
  repository_id = "embro"
  image_name    = "embro-ui:latest"
}

resource "google_cloud_run_v2_service" "embro-ui" {
  name                = "embro-ui"
  location            = "europe-west1"
  deletion_protection = false
  ingress             = "INGRESS_TRAFFIC_ALL"

  template {
    service_account = google_service_account.embro-ui.email

    containers {
      image = data.google_artifact_registry_docker_image.embro-ui.self_link
      ports {
        container_port = 3000
      }
    }

    scaling {
      min_instance_count = 0
      max_instance_count = 1
    }
  }
}

resource "google_cloud_run_v2_service_iam_member" "embro-ui" {
  name     = google_cloud_run_v2_service.embro-ui.name
  location = google_cloud_run_v2_service.embro-ui.location
  role     = "roles/run.invoker"
  member   = "allUsers"
}
