import React from 'react';
import {Card, CardMedia, styled} from '@mui/material';

const CustomCard = styled(Card)(({theme}) => ({
    display: 'flex',
    flexDirection: 'column',
    height: '80%', // 카드의 높이를 100%로 설정합니다.
}));

const CustomCardMedia = styled(CardMedia)(({theme}) => ({
    paddingTop: '100%', // 이미지의 세로 길이를 조정합니다. 가로 길이와 동일하게 설정합니다.
    backgroundSize: 'cover', // 이미지가 카드 내에 맞게 조정되도록 설정합니다.
}));

export default function MainFeaturedPost(props) {
    const {post} = props;

    return (
        <CustomCard>
            <CustomCardMedia
                image={post.image}
                title={post.imageTitle}
            />
        </CustomCard>
    );
}