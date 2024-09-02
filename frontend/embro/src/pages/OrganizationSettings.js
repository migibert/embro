import { Grid2 as Grid, Typography } from "@mui/material";
import SeniorityList from "../components/SeniorityList";
import SkillList from "../components/SkillList";

function OrganizationSettings() {

  return (
    <div>
      <Typography variant='h1'>Settings</Typography>
      <Grid container spacing={2} columns={16}>
        <Grid size={8}>
          <SkillList />
        </Grid>
        <Grid size={8}>
          <SeniorityList />
        </Grid>
      </Grid>
    </div>
  );
}

export default OrganizationSettings;