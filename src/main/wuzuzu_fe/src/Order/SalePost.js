import React from "react";
import {
  Avatar,
  Box,
  Card,
  CardActionArea,
  CardContent,
  Chip,
  Divider,
  Grid,
  Typography,
} from "@mui/material";
import {Visibility as VisibilityIcon} from "@mui/icons-material";

function SalePost({post, handlePostClick}) {
  return (
      <Grid item xs={12} sm={6} md={6}>
        <Card sx={{
          borderRadius: "20px",
          boxShadow: "0px 4px 10px rgba(0, 0, 0, 0.1)",
          position: "relative"
        }}>
          <CardActionArea onClick={() => handlePostClick(post)}>
            <CardContent>
              <Typography variant="h6" component="div" gutterBottom>
                {post.title}
              </Typography>
              <Chip
                  label={post.category}
                  color="primary"
                  size="small"
                  sx={{mb: 2}}
              />
              <Divider sx={{mb: 2}}/>
              <Box
                  sx={{display: "flex", alignItems: "center", mb: 2}}>
                <Avatar
                    alt={post.author}
                    src="https://example.com/avatar.jpg"
                    sx={{width: 32, height: 32, mr: 1}}
                />
                <Typography
                    variant="subtitle1">{post.author}</Typography>
              </Box>
              <Box sx={{display: "flex", alignItems: "center"}}>
                <VisibilityIcon fontSize="small" sx={{mr: 1}}/>
                <Typography
                    variant="body2">{post.views}</Typography>
              </Box>
            </CardContent>
          </CardActionArea>
          {post.stock === 0 && (
              <Box
                  sx={{
                    position: "absolute",
                    top: 0,
                    left: 0,
                    width: "100%",
                    height: "100%",
                    backgroundColor: "rgba(255, 255, 255, 0.8)",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                    zIndex: 1
                  }}
              >
                <Typography variant="h6" color="error">
                  품절
                </Typography>
              </Box>
          )}
        </Card>
      </Grid>
  );
}

export default SalePost;