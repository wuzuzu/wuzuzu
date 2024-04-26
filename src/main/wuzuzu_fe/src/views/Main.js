import * as React from 'react';
import Grid from '@mui/material/Grid';
import Container from '@mui/material/Container';
import {styled} from '@mui/material/styles';
import Header from './Header';
import MainFeaturedPost from './MainFeaturedPost';
import Footer from './Footer';
import MypageMap from "../views/MypageMap";

const sections = [
    {title: '🐶스팟 조회', url: '/Spot'},
    {title: '🐹 용품 거래', url: '/transaction'},
    {title: '🐱 커뮤니티', url: '/community'}
];

const mainFeaturedPosts = [
    {
        image: 'https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2F7da15d97-3fa8-4417-be84-ba19d03f3cd8%2FUntitled.png?table=block&id=1fa076c8-06a3-408c-8d23-a524b00022b4&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=1150&userId=&cache=v2',
    },
    {
        image: 'https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2Ff1d77172-193a-4235-ac52-395096e19920%2FKakaoTalk_20240326_203459622.jpg?table=block&id=0224d8bd-233c-4a40-9257-48d751890520&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=&cache=v2',
    },
    {
        image: 'https://teamsparta.notion.site/image/https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F83c75a39-3aba-4ba4-a792-7aefe4b07895%2Fa4be6608-96ba-4621-a522-d0ffc1f60def%2FUntitled.jpeg?table=block&id=3b370cbc-e1a9-4dfd-b187-c7f79095e67c&spaceId=83c75a39-3aba-4ba4-a792-7aefe4b07895&width=2000&userId=&cache=v2',
    }
];

const StyledGrid = styled(Grid)({
    '&.MuiGrid-root': {
        border: 'none', // 그리드의 외곽선을 제거합니다.
    },
    '& .MuiGrid-item': {
        borderBottom: 'none', // 그리드 아이템의 하단 선을 제거합니다.
    },
});

export default function Main() {
    return (
        <div style={{backgroundColor: '#FBEFEF'}}>
            <Container maxWidth="lg" style={{backgroundColor: '#FFFFFF'}} sx={{
                borderBottom: 1,
                borderColor: 'divider',
                overflowX: 'auto'
            }}>
                <Header title="Wuzuzu" sections={sections}/>
                <main>
                    <br/>
                    <MypageMap/>
                    <br/>
                    <StyledGrid container spacing={3}>
                        {mainFeaturedPosts.map((post, index) => (
                            <Grid item key={index} xs={12} sm={6} md={4}
                                  style={{height: '500px'}}>
                                <MainFeaturedPost post={post}/>
                            </Grid>
                        ))}
                    </StyledGrid>
                </main>
            </Container>
            <Footer
                title="I끼리 힘내보조"
                description="쭈니 재롱 부송 쩹이"
            />
        </div>
    );
}
