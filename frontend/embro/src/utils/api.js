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

const createOrganization = async (token, organization) => {
    return _post(
        token, 
        `${BASE_URL}/organizations/`, 
        JSON.stringify(organization)
    );
};

const deleteOrganization = async (token, id) => {
    return _delete(token, `${BASE_URL}/organizations/${id}`);
}

const listTeams = async (token, organizationId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/teams/`);
};

const getTeam = async (token, organizationId, teamId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/teams/${teamId}`);
};

const listTeamMembers = async (token, organizationId, teamId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/teams/${teamId}/members/`); 
};

const addTeamMember = async (token, organizationId, teamId, collaboratorId, keyPlayer) => {
    return _put(
        token,
        `${BASE_URL}/organizations/${organizationId}/teams/${teamId}/members/${collaboratorId}`,
        JSON.stringify({keyPlayer: keyPlayer})
    );
};

const removeTeamMember = async (token, organizationId, teamId, collaboratorId) => {
    return _delete(
        token,
        `${BASE_URL}/organizations/${organizationId}/teams/${teamId}/members/${collaboratorId}`,
    );
};

const createTeam = async (token, organizationId, team) => {
    return _post(
        token,
        `${BASE_URL}/organizations/${organizationId}/teams/`, 
        JSON.stringify(team)
    );
};

const updateTeam = async (token, organizationId, team) => {
    return _put(
        token,
        `${BASE_URL}/organizations/${organizationId}/teams/${team.id}`,
        JSON.stringify(team)
    );
}

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

const listPositions = async (token, organizationId) => {
    return _get(token, `${BASE_URL}/organizations/${organizationId}/positions/`);
};

const createPosition = async (token, organizationId, name) => {
    return _post(
        token, 
        `${BASE_URL}/organizations/${organizationId}/positions/`,
        JSON.stringify({id: null, name: name}));
};

const deletePosition = async (token, organizationId, positionId) => {
    return _delete(token, `${BASE_URL}/organizations/${organizationId}/positions/${positionId}`);
}

const acceptInvitation = async (token, invitationId) => {
    return _get(token, `${BASE_URL}/invitations/${invitationId}`);
}

const createInvitation = async (token, email, organizationId, role) => {
    return _post(token, `${BASE_URL}/invitations/`, JSON.stringify({email: email, organizationId: organizationId, role: role}));
}

export {
    acceptInvitation, addTeamMember, createCollaborator, createInvitation, createOrganization, createPosition, createSeniority, createSkill, createTeam, deleteCollaborator, deleteOrganization, deletePosition, deleteSeniority, deleteSkill, deleteTeam, getTeam,
    listCollaborators, listOrganizations, listPositions, listSeniorities, listSkills, listTeamMembers, listTeams, removeTeamMember, updateCollaborator, updateTeam
};

