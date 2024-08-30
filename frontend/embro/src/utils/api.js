const get = async (token, url) => {
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
        return responseData;
    } catch (error) {
        console.log(error.message);
    }
};

const post = async (token, url, body) => {
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
        console.log("response", responseData)
        return responseData;
    } catch (error) {
        console.log(error.message);
    }
};

const listOrganizations = async (token) => {
    return get(token, 'http://localhost:8080/organizations/');
}
export { get, listOrganizations, post };

