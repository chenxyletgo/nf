import { createRouter, createWebHistory } from 'vue-router';
import { isLogin } from '../fun';

const router = createRouter({
    routes: [
        { path: '/', redirect: '/home' },
        { name: 'home', path: '/home', redirect: '/home/userlist', component: () => import('../components/Home.vue'), 
            children: [
                {name: 'userlist', path: 'userlist', component: () => import('../views/UserList.vue'), },
                {name: 'articlelist', path: 'articlelist', component: () => import('../views/ArticleList.vue'), },
                {name: 'articlereview', path: 'articlereview', component: () => import('../views/ArticleReview.vue'),},
                {name: 'categorylist', path: 'categorylist', component: () => import('../views/CategoryList.vue'), },
                {name: 'categorytree', path: 'categorytree', component: () => import('../views/CategoryTree.vue'),},
                // 测试
                {name: 'articletest', path: 'articletest', component: () => import('../views/Test.vue'),}
            ],
        },
        { name: 'login', path: '/login', component: () => import('../views/Login.vue'), },
        { name: 'preview', path: '/preview/:id', component: () => import('../views/Preview.vue'), }
    ],
    history: createWebHistory(),
});

// 路由拦截器
router.beforeEach((to, from) => {
    // 未登录就跳到登录页面
    if (to.name !== 'login' && (!isLogin())) {
        return { name: 'login'};
    }
    return true;
});

export default router;