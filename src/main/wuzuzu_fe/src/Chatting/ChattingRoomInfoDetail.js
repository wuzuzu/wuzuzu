import React from "react";
import {Box, Button, Chip, Typography} from "@mui/material";
import {style} from "./ChattingApp";

function ChattingRoomInfoDetail({room, onBackClick}) {
    return (
        <Box
            sx={{
                ...style,
                bgcolor: 'white',
            }}
            variant="plain"
        >
            <Box
                sx={{
                    height: '100%',
                    backgroundImage: room.coverImage ? `url(${room.coverImage})`
                        : 'none',
                    backgroundSize: 'cover',
                    backgroundPosition: 'center',
                    backgroundColor: '#6E6E6E',
                    borderRadius: '30px',
                    display: 'flex',
                    flexDirection: 'column',
                    justifyContent: 'flex-end',
                }}
            >
                <Box
                    sx={{
                        p: 3,
                        display: 'flex',
                        flexDirection: 'column',
                        gap: 2,
                        backgroundColor: 'rgba(0, 0, 0, 0)',
                        borderRadius: '30px 30px 0 0',
                    }}
                >
                    <Typography variant="h4" sx={{color: 'white'}}>
                        {room.chatRoomName}
                    </Typography>
                    <Typography variant="body1" sx={{
                        color: "white",
                        overflow: "hidden",
                        textOverflow: "ellipsis",
                        whiteSpace: "nowrap",
                    }}>
                        {room.description}
                    </Typography>
                    {room.chatRoomTags.length > 0 && room.chatRoomTags[0] !== ""
                        && (
                            <Box sx={{
                                display: 'flex',
                                flexWrap: 'wrap',
                                gap: 1
                            }}>
                                {room.chatRoomTags.map((tag) => (
                                    <Chip
                                        key={tag}
                                        size="small"
                                        label={`#${tag}`}
                                        sx={{
                                            borderRadius: '20px',
                                            color: 'white',
                                            backgroundColor: '#0489B1',
                                        }}
                                    />
                                ))}
                            </Box>
                        )}
                    <Box sx={{
                        mt: 3,
                        display: 'flex',
                        justifyContent: 'center',
                        gap: 2
                    }}>
                        <Button
                            variant="contained"
                            onClick={onBackClick}
                            sx={{
                                borderRadius: '20px',
                                color: 'white',
                                "&:hover": {
                                    color: 'black',
                                    borderColor: 'white',
                                },
                            }}
                        >
                            닫기
                        </Button>
                    </Box>
                </Box>
            </Box>
        </Box>
    );
}

export default ChattingRoomInfoDetail;