import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
    routes: [
        { path: '/', redirect: '/home'},
        {
            name: 'home',
            path: '/home',
            redirect: '/home/recommend',
            children: [
                {
                    name: 'category',
                    path: ':category',
                    component: () => import('../views/Home.vue'),
                }
            ],
        },
        {
            name: 'login',
            path: '/login',
            component: () => import('../views/Login.vue'),
        },
        {
            name: 'register',
            path: '/register',
            component: () => import('../views/Register.vue'),
            meta: {
                keepAlive: true,
            },
        },
        {
            name: 'article',
            path: '/article/:id',
            component: () => import('../views/Article.vue'),
        },
        {
            name: 'makeari',
            path: '/editarticle',
            component: () => import('../views/EditArticle.vue'),
        },
        {
            name: 'editari',
            path: '/editarticle/:id',
            component: () => import('../views/EditArticle.vue'),
        },
        {
            name: 'usercenter',
            path: '/usercenter',
            redirect: '/usercenter/info',
            component: () => import('../views/UserCenter.vue'),
            children: [
                {
                    name: 'info',
                    path: 'info',
                    component: () => import('../components/usercenter/Info.vue'),
                },
                {
                    name: 'mesg',
                    path: 'mesg',
                    component: () => import('../components/usercenter/Mesg.vue'),
                },
                {
                    name: 'atce',
                    path: 'atce',
                    component: () => import('../components/usercenter/Atce.vue'),
                },
                {
                    name: 'cmet',
                    path: 'cmet',
                    component: () => import('../components/usercenter/Cmet.vue'),
                },
            ],
        },
        {
            name: 'search',
            path: '/search',
            component: () => import('../views/Search.vue'),
        },
        {
            name: 'preview',
            path: '/preview/:id',
            component: () => import('../views/Preview.vue'),
        },
        {
            name: 'test',
            path: '/test',
            component: () => import('../test/Test.vue'),
        }
    ],
    history: createWebHistory(),
});

// 路由拦截器
router.beforeEach((to, from) => {
    return true;
});

export default router;