<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { getPreviewArticleB } from '../api/articleapi';
const route = useRoute();
const article = reactive({
    id: '',
    firstCateName: '',
    title: '',
    text: '',
    userName: '',
    userAvatar: '',
});
function getArticleId() {
    if (route.params && route.params.id) {
        article.id = route.params.id as string;
    }
}
function getArticleById(id: string) {
    getPreviewArticleB(id).then(res => {
        Object.assign(article, res.data.article);
    });
}
onMounted(() => {
    getArticleId();
    getArticleById(article.id);
});
</script>

<template>
    <div class="news-body">
        <div class="header">
            <a>{{ article.firstCateName === null ? '其他' : article.firstCateName}}</a>
            <span class="dividers">/</span>
            <span>{{ article.title }}</span>
        </div>
        <div class="content">
            <div class="content-body">
                <div class="title">
                    <h1>{{ article.title }}</h1>
                    <div class="info min-font">
                        <span>0000-00-00 00:00:00</span>
                        <span>-- 阅读</span>
                        <span>-- 喜欢</span>
                        <span>-- 评论</span>
                    </div>
                </div>

                <div class="author">
                    <div class="left">
                        <div class="img-div">
                            <a>
                                <img :src="article.userAvatar === null ? '/defaultavatar.jpg' : article.userAvatar" />
                            </a>
                        </div>
                        <div class="author-info">
                            <div class="name"><a href="">{{ article.userName }}</a></div>
                            <div class="info-info min-font">
                                <span>粉丝：--</span>
                                <span>文章：--</span>
                            </div>
                        </div>
                    </div>
                    <div class="right"></div>
                </div>
                <div class="div-line"></div>
                <div class="text">
                    <v-md-editor :model-value="article.text" mode="preview"></v-md-editor>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.news-body {
    width: 900px;
    margin: 0 auto;
}
.header {
    font-size: 12px;
    margin: 10px 0 12px 0;
}
.header a {
    color: rgba(0,0,0,.45);
    text-decoration: none;
}
.dividers {
    margin: 8px;
}
.content {
    width: 820px;
    padding: 30px 40px 40px 40px;
    margin-bottom: 12px;
}
.min-font {
    font-size: 13px;
    color: #999;
}
.title {
    margin: 0 80px 20px 80px;
}
.info span {
    margin-right: 20px;
}
.author {
    margin: 0 80px 20px 80px;
}
.author .left {
    display: flex;
}
.img-div img {
    width: 44px;
    height: 44px;
    border-radius: 50%;
    margin-right: 10px;
}
.author-info .name a {
    vertical-align: middle;
    text-decoration: none;
    color: #212121;
    font-size: 14px;
}
.author-info .info-info span {
    margin-right: 20px;
}
.div-line {
    height: 1px;
    width: 100%;
    background: hsla(0,0%,59.2%,.21);
    margin-bottom: 40px;
}
</style>