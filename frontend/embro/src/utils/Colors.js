const getOrganizationHue = (organizationId) => {
  let hash = 0;
  for (let i = 0; i < organizationId.length; i++) {
    hash = organizationId.charCodeAt(i) + ((hash << 5) - hash);
    hash = hash & hash;
  }
  return hash % 360;
}

const getOrganizationColor = (organizationId, lightness) => {
  const hue = getOrganizationHue(organizationId);
  const saturation = 30;
  const light = lightness % 100;
  return `hsl(${hue}, ${saturation}%, ${light}%)`;
}

export { getOrganizationColor };
