import React from 'react';
import {
    Avatar,
    Box,
    Button,
    Card,
    CardContent,
    CardMedia,
    Typography,
} from '@mui/material';
import {
    Comment as CommentIcon,
    Favorite as FavoriteIcon,
    Visibility as VisibilityIcon
} from '@mui/icons-material';

function CommunityPostDetail({post, handleBackClick}) {
    return (
        <Box sx={{display: 'flex', justifyContent: 'center'}}>
            <Card sx={{maxWidth: '800px', width: '100%'}}>
                {post.image && (
                    <CardMedia
                        component="img"
                        height="400"
                        image={post.image}
                        alt={post.title}
                    />
                )}
                <CardContent>
                    <Typography variant="h4" component="div" gutterBottom>
                        {post.title}
                    </Typography>
                    <Box sx={{display: 'flex', alignItems: 'center', mb: 2}}>
                        <Avatar
                            alt={post.username}
                            src={post.userAvatar}
                            sx={{width: 32, height: 32, mr: 1}}
                        />
                        <Typography
                            variant="subtitle1">{post.username}</Typography>
                        <Typography variant="body2" color="text.secondary"
                                    sx={{ml: 'auto'}}>
                            {post.date}
                        </Typography>
                    </Box>
                    <Typography variant="body1"
                                sx={{whiteSpace: 'pre-line', mb: 4}}>
                        {post.contents}
                    </Typography>
                    <Box sx={{
                        display: 'flex',
                        alignItems: 'center',
                        justifyContent: 'space-between'
                    }}>
                        <Box sx={{display: 'flex', alignItems: 'center'}}>
                            <FavoriteIcon fontSize="small" sx={{mr: 1}}/>
                            <Typography
                                variant="body2">{post.likeCount}</Typography>
                            <VisibilityIcon fontSize="small"
                                            sx={{ml: 2, mr: 1}}/>
                            <Typography
                                variant="body2">{post.views}</Typography>
                            <CommentIcon fontSize="small" sx={{ml: 2, mr: 1}}/>
                            <Typography
                                variant="body2">{post.comments}</Typography>
                        </Box>
                        <Button variant="contained" onClick={handleBackClick}>
                            목록으로
                        </Button>
                    </Box>
                </CardContent>
            </Card>
        </Box>
    );
}

export default CommunityPostDetail;