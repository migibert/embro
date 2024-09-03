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


const listOrganizations = async (token) => {
    return _get(token, 'http://localhost:8080/organizations/');
};

const createOrganization = async (token, name) => {
    return _post(
        token, 
        'http://localhost:8080/organizations/', 
        JSON.stringify({id: null, name: name})
    );
};

const listTeams = async (token, organizationId) => {
    return _get(token, `http://localhost:8080/organizations/${organizationId}/teams/`);
};

const createTeam = async (token, organizationId, name) => {
    return _post(
        token,
        `http://localhost:8080/organizations/${organizationId}/teams/`, 
        JSON.stringify({id: null, name: name})
    );
};

const listSkills = async (token, organizationId) => {
    return _get(token, `http://localhost:8080/organizations/${organizationId}/skills/`);
};

const createSkill = async (token, organizationId, name) => {
    return _post(
        token,
        `http://localhost:8080/organizations/${organizationId}/skills/`, 
        JSON.stringify({id: null, name: name})
    );
};

const listSeniorities = async (token, organizationId) => {
    return _get(token, `http://localhost:8080/organizations/${organizationId}/seniorities/`);
};

const createSeniority = async (token, organizationId, name) => {
    return _post(
        token,
        `http://localhost:8080/organizations/${organizationId}/seniorities/`, 
        JSON.stringify({id: null, name: name})
    );
};

const listCollaborators = async (token, organizationId) => {
    return _get(token, `http://localhost:8080/organizations/${organizationId}/collaborators/`);
};

const deleteCollaborator = async (token, organizationId, collaboratorId) => {
    return _delete(token, `http://localhost:8080/organizations/${organizationId}/collaborators/${collaboratorId}`);
}

const createCollaborator = async (token, organizationId, collaborator) => {
    return _post(
        token,
        `http://localhost:8080/organizations/${organizationId}/collaborators/`,
        JSON.stringify(collaborator)
    );
};

export {
    createCollaborator, createOrganization, createSeniority, createSkill, createTeam, deleteCollaborator, listCollaborators, listOrganizations, listSeniorities, listSkills, listTeams
};

