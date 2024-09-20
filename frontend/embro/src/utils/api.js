const BASE_URL = `${process.env.REACT_APP_EMBRO_API_BASE_URL}`;

const _get = async (token, url) => {
    try {
        const response = await fetch(
            url,
            {
                headers: {
                    method: 'GET',
                    Authorization: `Bearer ${token}`,
                    Accept: 'application/json',
                },
            }
        );
        const responseData = await response.json(); 
        if(responseData.error) {
            throw new Error(responseData.error);
        }
        return responseData;
    } catch (error) {
        console.error(error.message);
        throw error;
    }
};

const _post = async (token, url, body) => {
    try {
        const response = await fetch(
            url,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: body,
                method: "POST",
            }
        );
        const responseData = await response.json();
        if(responseData.error) {
            throw new Error(responseData.error);
        }
        return responseData;
    } catch (error) {
        console.error(error.message);
        throw error;
    }
};

const _put = async (token, url, body) => {
    try {
        const response = await fetch(
            url,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: body,
                method: "PUT",
            }
        );
        const responseData = await response.json();
        if(responseData.error) {
            throw new Error(responseData.error);
        }
        return responseData;
    } catch (error) {
        console.error(error.message);
        throw error;
    }
};

const _delete = async (token, url) => {
    try {
        const response = await fetch(
            url,
            {
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                method: "DELETE",
            }
        );
        if(response.status !== 204) {
            throw new Error(`${response.status}-${response.statusText}`);
        }
    } catch (error) {
        console.error(error.message);
        throw error;
    }
};


const listOrganizations = async (token) => {
    return _get(token, `${BASE_URL}/organizations/`);
};

const createOrganization = async (token, name) => {
    return _post(
        token, 
        `${BASE_URL}/organizations/`, 
        JSON.stringify({id: null, name: name})
    );
};

const deleteOrganization = async (token, id) => {
    return _delete(token, `${BASE_URL}/organizations/${id}`);
}

const listTeams = async (token, organizationId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/teams/`);
};

const listTeamMembers = async (token, organizationId, teamId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/teams/${teamId}/members/`); 
};

const createTeam = async (token, organizationId, team) => {
    return _post(
        token,
        `${BASE_URL}/organizations/${organizationId}/teams/`, 
        JSON.stringify(team)
    );
};

const deleteTeam = async (token, organizationId, teamId) => {
    return _delete(token, `${BASE_URL}/organizations/${organizationId}/teams/${teamId}`);
}

const listSkills = async (token, organizationId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/skills/`);
};

const createSkill = async (token, organizationId, name) => {
    return _post(
        token,
        `${BASE_URL}/organizations/${organizationId}/skills/`, 
        JSON.stringify({id: null, name: name})
    );
};

const deleteSkill = async (token, organizationId, skillId) => {
    return _delete(token, `${BASE_URL}/organizations/${organizationId}/skills/${skillId}`);
};

const listSeniorities = async (token, organizationId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/seniorities/`);
};

const createSeniority = async (token, organizationId, name) => {
    return _post(
        token,
        `${BASE_URL}/organizations/${organizationId}/seniorities/`, 
        JSON.stringify({id: null, name: name})
    );
};

const deleteSeniority = async (token, organizationId, seniorityId) => {
    return _delete(token, `${BASE_URL}/organizations/${organizationId}/seniorities/${seniorityId}`);
};

const listCollaborators = async (token, organizationId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/collaborators/`);
};

const updateCollaborator = async (token, organizationId, collaborator) => {
    return _put(token, `${BASE_URL}/organizations/${organizationId}/collaborators/${collaborator.id}`, JSON.stringify(collaborator));
}

const deleteCollaborator = async (token, organizationId, collaboratorId) => {
    return _delete(token, `${BASE_URL}/organizations/${organizationId}/collaborators/${collaboratorId}`);
}

const createCollaborator = async (token, organizationId, collaborator) => {
    return _post(
        token,
        `${BASE_URL}/organizations/${organizationId}/collaborators/`,
        JSON.stringify(collaborator)
    );
};

const listRoles = async (token, organizationId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/roles/`);
};

const createRole = async (token, organizationId, name) => {
    return _post(
        token, 
        `${BASE_URL}/organizations/${organizationId}/roles/`,
        JSON.stringify({id: null, name: name}));
};

const deleteRole = async (token, organizationId, roleId) => {
    return _delete(token, `${BASE_URL}/organizations/${organizationId}/roles/${roleId}`);
}


export {
    createCollaborator, createOrganization, createRole, createSeniority, createSkill, createTeam,
    deleteCollaborator, deleteOrganization, deleteRole, deleteSeniority, deleteSkill, deleteTeam,
    listCollaborators, listOrganizations, listRoles, listSeniorities, listSkills, listTeamMembers, listTeams, updateCollaborator
};

