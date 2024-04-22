import React from "react";
import {ListItemAvatar, ListItemButton, ListItemText} from "@mui/material";
import Avatar from "@mui/material/Avatar";

function ChattingRoomInfo({room, selected, onClick, onChatRoomInfoClick}) {

    function handleChatRoomInfo() {
        onChatRoomInfoClick(room);
    }

    return (
        <ListItemButton
            selected={selected}
            onClick={onClick}
            sx={{
                '&.Mui-selected': {
                    backgroundColor: 'primary.main',
                    color: 'white',
                    '&:hover': {
                        backgroundColor: 'primary.dark',
                    },
                },
            }}
        >
            <ListItemAvatar>
                <Avatar alt="DefaultChatRoomImage"
                        src="images/default_chat_room_image.png"
                        onClick={handleChatRoomInfo}/>
            </ListItemAvatar>
            <ListItemText primary={room.chatRoomName}
                          secondary={room.description}
                          sx={{
                              '& .MuiListItemText-primary': {
                                  color: 'inherit',
                              },
                              '& .MuiListItemText-secondary': {
                                  color: 'inherit',
                              },
                          }}/>
        </ListItemButton>
    );
}

export default ChattingRoomInfo;