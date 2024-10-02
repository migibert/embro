import { useAuth0 } from "@auth0/auth0-react";
import { Box, Grid2 as Grid, Typography } from "@mui/material";
import { useContext, useEffect, useState } from "react";
import FieldsetList from "../components/FieldsetList";
import { OrganizationContext } from "../context/OrganizationContext";
import { createPosition, createSeniority, createSkill, deletePosition, deleteSeniority, deleteSkill, listPositions, listSeniorities, listSkills } from "../utils/api";

function OrganizationSettings() {
  const { getAccessTokenSilently } = useAuth0();
  const { currentOrganization } = useContext(OrganizationContext);
  const [skills, setSkills] = useState([]);
  const [seniorities, setSeniorities] = useState([]);
  const [positions, setPositions] = useState([]);

  const addSkill = async (name) => {
    const token = await getAccessTokenSilently();
    const created = await createSkill(token, currentOrganization.id, name);
    setSkills([...skills, created]);
  };

  const removeSkill = async (id) => {
    const token = await getAccessTokenSilently();
    await deleteSkill(token, currentOrganization.id, id);
    setSkills(skills.filter((skill) => skill.id !== id));
  }

  const addPosition = async (name) => {
    const token = await getAccessTokenSilently();
    const created = await createPosition(token, currentOrganization.id, name);
    setPositions([...positions, created]);
  };

  const removePosition = async (id) => {
    const token = await getAccessTokenSilently();
    await deletePosition(token, currentOrganization.id, id);
    setPositions(positions.filter((position) => position.id !== id));
  }

  const addSeniority = async (name) => {
    const token = await getAccessTokenSilently();
    const created = await createSeniority(token, currentOrganization.id, name);
    setSeniorities([...seniorities, created]);
  };

  const removeSeniority = async (id) => {
    const token = await getAccessTokenSilently();
    await deleteSeniority(token, currentOrganization.id, id);
    setSeniorities(seniorities.filter((seniority) => seniority.id !== id));
  }

  useEffect(() => {
    const loadSeniorities = async () => {
      const token = await getAccessTokenSilently();
      const loadedSeniorities = await listSeniorities(token, currentOrganization.id);
      setSeniorities(loadedSeniorities);
    };

    const loadPositions = async () => {
      const token = await getAccessTokenSilently();
      const loadedPositions = await listPositions(token, currentOrganization.id);
      setPositions(loadedPositions);
    };

    const loadSkills = async () => {
      const token = await getAccessTokenSilently();
      const loadedSkills = await listSkills(token, currentOrganization.id);
      setSkills(loadedSkills);
    };

    const load = async () => {
      if(!currentOrganization) {
        return;
      }
      await Promise.all([
        loadSkills(), 
        loadPositions(), 
        loadSeniorities()
      ]);
    };
    load();
  }, [
    getAccessTokenSilently,
    currentOrganization
  ]);

  return (
    <Box>
      <Typography variant='h1'>Settings</Typography>
      <Grid container spacing={2} columns={12}>
        <Grid size={4}>
          <FieldsetList 
            title='Skills' 
            items={skills} 
            onSave={(name) => addSkill(name)} 
            onDelete={(id) => removeSkill(id)}
          />
        </Grid>
        <Grid size={4}>
          <FieldsetList 
            title='Positions' 
            items={positions} 
            onSave={(name) => addPosition(name)} 
            onDelete={(id) => removePosition(id)}
          />
        </Grid>
        <Grid size={4}>
          <FieldsetList 
            title='Seniorities' 
            items={seniorities} 
            onSave={(name) => addSeniority(name)} 
            onDelete={(id) => removeSeniority(id)}
          />
        </Grid>
      </Grid>
    </Box>
  );
}

export default OrganizationSettings;