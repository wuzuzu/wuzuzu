import React from "react";
import {ListItemAvatar, ListItemButton, ListItemText} from "@mui/material";
import Avatar from "@mui/material/Avatar";

function ChattingRoom(props) {
    const room = props.props;
    const avatar = room.src;
    const primary = room.primary;
    const secondary = room.secondary;

    return (
        <ListItemButton>
            <ListItemAvatar>
                <Avatar alt="Profile Picture" src={avatar}/>
            </ListItemAvatar>
            <ListItemText primary={primary}
                          secondary={secondary}/>
        </ListItemButton>
    );
}

export default ChattingRoom;