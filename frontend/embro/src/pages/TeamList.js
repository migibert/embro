import { Grid2 as Grid } from "@mui/material";
import TeamCard from "./TeamCard";

function TeamList({teams, onDelete, onSelect}) {
  return (
    <Grid container spacing={2} columns={12} justifyItems={'flex-start'} width={'fit-content'}>
      {teams?.map((team) => (
        <Grid item xs>
          <TeamCard team={team} onSelect={onSelect} onDelete={onDelete}/>
        </Grid>
      ))}
    </Grid>
  )
}

export default TeamList;