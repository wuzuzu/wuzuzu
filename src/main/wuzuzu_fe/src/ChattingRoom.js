import React from "react";
import {ListItemAvatar, ListItemButton, ListItemText} from "@mui/material";
import Avatar from "@mui/material/Avatar";

function ChattingRoom(props) {
    const room = props.props;

    return (
        <ListItemButton>
            <ListItemAvatar>
                <Avatar alt="Profile Picture" src=""/>
            </ListItemAvatar>
            <ListItemText primary={room.chatRoomName}
                          secondary={room.description}/>
        </ListItemButton>
    );
}

export default ChattingRoom;