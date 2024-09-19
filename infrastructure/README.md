# CloudSQL database

Use the scripts provided in `scripts` directory:

For connecting to the database:

```
./dbadmin connect
```

For setting the database up (by default IAM users do not have any privilege)

```
./dbadmin init
```

> [!NOTE]
> A temporary user is generated for according privilege to the Backend application dedicated service account. It is deleted at the end of the process.

