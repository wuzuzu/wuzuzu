import React from 'react';
import { makeStyles } from '@mui/styles';
import Card from '@mui/material/Card';
import CardMedia from '@mui/material/CardMedia';

const useStyles = makeStyles((theme) => ({
  card: {
    display: 'flex',
    flexDirection: 'column',
    height: '80%', // 카드의 높이를 100%로 설정합니다.
  },
  media: {
    paddingTop: '100%', // 이미지의 세로 길이를 조정합니다. 가로 길이와 동일하게 설정합니다.
    backgroundSize: 'cover', // 이미지가 카드 내에 맞게 조정되도록 설정합니다.
  },
}));

export default function MainFeaturedPost(props) {
  const classes = useStyles();
  const { post } = props;

  return (
      <Card className={classes.card}>
        <CardMedia
            className={classes.media}
            image={post.image}
            title={post.imageTitle}
        />
      </Card>
  );
}
