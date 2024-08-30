import { Box, Typography } from "@mui/material";

function Footer() {
    return (
    <Box
        component="footer"
        sx={{
          py: 2,
          px: 2,
          mt: 'auto',
          backgroundColor: (theme) =>
            theme.palette.mode === 'light'
              ? theme.palette.grey[200]
              : theme.palette.grey[800],
          width: '100%',
          textAlign: 'center',
        }}
      >
        <Typography variant="body1">Footer</Typography>
      </Box>
    );
}

export default Footer;