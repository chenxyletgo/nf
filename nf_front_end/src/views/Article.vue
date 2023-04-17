<script setup lang='ts'>
import { ref, reactive, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { flushedComm } from '../store';
import { getArticleB } from "../api/articleapi";
import { getCommentListB } from '../api/commentapi';
import Text from '../components/article/Text.vue';
import Reply from '../components/article/Reply.vue';
import Comment from '../components/article/Comment.vue';
const route = useRoute();
const article = reactive({
    id: '',
    title: '',
    text: '',
    userId: '',
    userAvatar: '',
    userName: '',
    time: '',
    watch: 0,
    like: 0,
    likeActive: false,
    commentCount: 0,
    userFanCount: 0,
    fanActive: false,
    userArticleCount: 0,
    firstCateId: '',
    firstCateName: '',
    firstCatePathName: '',
});
const replyData = reactive({
    id: '', // 文章id
    ruserId: 'no1', //  no1一级 no2二级 id三级
});
const comments = ref();
const page = reactive({
    id: '', // 文章id
    level: 0, // 0一级评论 1二级评论
    total: 0,
    size: 14,
    cur: 1,
});
const pageWatch = reactive({
    sort: 0, //0表示热度-看点赞数 1表示时间排序
});
function changPage(cur: number = 1) {
    getCommentListB(Object.assign(page, pageWatch), cur, page.size).then(res => {
        comments.value = res.data.list;
        page.total = res.data.total;
    });
}
function getArticle() {
    getArticleB(article.id, 2).then(res => {
        Object.assign(article, res.data.article);
    });
}
function getArticleId() {
    if (route.params && route.params.id) article.id = route.params.id as string;
}
watch(pageWatch, () => {
    changPage();
})
watch(flushedComm, () => {
    // TODO 该处可以优化，不需要将整个文章获取
    getArticle();
    // 评论跟新
    changPage(page.cur);
});
onMounted(() => {
    getArticleId();
    getArticle();
    page.id = article.id;
    replyData.id = article.id;
    changPage();
});
</script>

<template>
     <div class="news-body">
        <div class="header">
            <a :href="'/home/' + article.firstCatePathName">{{ article.firstCateName === null ? '其他' : article.firstCateName }}</a>
            <span class="dividers">/</span>
            <span>{{ article.title }}</span>
        </div>
        <div class="content">
            <Text :init-like-acitve="article.likeActive" :data="article"></Text>
        </div>
        <div class="comment">
            <div class="comment-head">{{ article.commentCount === null ? '0' : article.commentCount }} 评论</div>
            <div class="comment-content">
                <div class="sort">
                    <ul>
                        <li :class="pageWatch.sort === 0 ? 'on' : ''" @click="pageWatch.sort = 0">按热度排序</li>
                        <li :class="pageWatch.sort === 1 ? 'on' : ''" @click="pageWatch.sort = 1">按时间排序</li>
                    </ul>
                </div>
                <div class="reply">
                    <Reply :data="replyData" />
                </div>
                <div class="comment-list">
                    <Comment :data="item" v-for="(item, index) in comments" :key="index"></Comment>
                </div>
            </div>
        </div>
        <div class="div-100" v-if="!(page.total > page.size)"></div>
        <el-pagination
            class="page-div"  
            v-if="page.total > page.size"
            v-model:current-page="page.cur"
            v-model:page-size="page.size"
            layout="prev, pager, next, jumper"
            :total="page.total"
            :background="true"
            @current-change="changPage"
        />
    </div>
</template>

<style scoped>
.div-100 {
    height: 70px;
}
.page-div {
    justify-content: center;
    margin-top: 10px;
    margin-bottom: 70px;
}
.content, .comment {
    background-color: #fff;
    border-radius: 4px;
}
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
    border: 1px solid #e5e9ef;
}
.comment {
    width: 820px;
    padding: 20px 40px;
    border: 1px solid #e5e9ef;
}
.comment-head {
    font-size: 18px;
    margin-bottom: 20px;
}
.sort {
    margin-bottom: 24px;
}
.sort ul {
    list-style: none;
    padding: 0;
    margin: 0;
    display: flex;
    border-bottom: 1px solid #e5e9ef;
}
.sort ul li {
    font-size: 14px;
    font-weight: bold;
    padding: 8px 0;
    margin-right: 16px;
    cursor: pointer;
    transition: 0.2s;
}
.sort ul li:hover {
    color: #00a1d6;
}
.sort .on {
    border-bottom: 1px solid #00a1d6;
    color: #00a1d6;
}
.reply {
    margin: 10px 0;
    padding-top: 15px;
}
</style>