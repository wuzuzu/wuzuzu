import React, {useState, useEffect} from "react";
import {
    AppBar,
    Box, Fab,
    IconButton,
    List,
    Paper,
    styled,
    Toolbar
} from "@mui/material";
import ChattingRoom from "./ChattingRoom";
import AddIcon from "@mui/icons-material/Add";
import SearchIcon from "@mui/icons-material/Search";
import MoreIcon from "@mui/icons-material/MoreVert";

function ChattingRoomList() {

    const StyledFab = styled(Fab)({
        position: 'absolute',
        zIndex: 1,
        top: -30,
        left: 0,
        right: 0,
        margin: '0 auto',
    });

    return (
        <Box>
            <Paper>
                <List sx={{mb: 2}}>
                    <ChattingRoom props={{
                        avatar: "",
                        primary: "Primary",
                        secondary: "Secondary"
                    }}/>
                </List>
            </Paper>
            <AppBar position="fixed" color="primary"
                    sx={{top: 'auto', bottom: 0}}>
                <Toolbar>
                    <StyledFab color="secondary"
                               aria-label="add">
                        <AddIcon/>
                    </StyledFab>
                    <Box sx={{flexGrow: 1}}/>
                    <IconButton color="inherit">
                        <SearchIcon/>
                    </IconButton>
                    <IconButton color="inherit">
                        <MoreIcon/>
                    </IconButton>
                </Toolbar>
            </AppBar>
        </Box>
    );
}

export default ChattingRoomList;