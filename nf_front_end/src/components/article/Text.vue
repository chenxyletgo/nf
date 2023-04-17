<script setup lang='ts'>
import { ref, reactive, watch, onMounted, onUpdated } from 'vue';
import { addLikeAB } from '../../api/likeaapi';
let getData = defineProps<{
    data: {
        id: string,
        title: string,
        text: string,
        time: string,
        watch: number,
        like: number,
        likeActive: boolean,
        commentCount: number,
        userId: string,
        userAvatar: string,
        userName: string,
        userFanCount: number,
        userArticleCount: number,
    },
    initLikeAcitve: boolean,
}>();
let initAcitve: boolean;
const count = ref(0);
function addLike(id: string) {
    addLikeAB(id).then(res => {
        count.value++;
        getData.data.likeActive = getData.data.likeActive ? false : true;
    });
}
watch(count, (newV, oldV) => {
    if (oldV === 0) initAcitve = !getData.data.likeActive;
    if (initAcitve) { // 开始喜欢
        if (count.value % 2 === 0) getData.data.like++;
        else getData.data.like--;
    } else { // 开始不喜欢
        if (count.value % 2 === 0) getData.data.like--;
        else getData.data.like++;
    }
});
</script>

<template>
    <div class="content-body">
        <div class="title">
            <h1>{{ data.title }}</h1>
            <div class="info min-font">
                <span>{{ data.time }}</span>
                <span>{{ data.watch }} 阅读</span>
                <span @click="addLike(data.id)" v-if="data.likeActive" class="on like">{{ data.like === null ? '0' : data.like }} 喜欢</span>
                <span @click="addLike(data.id)" v-if="!data.likeActive" class="like">{{ data.like === null ? '0' : data.like }} 喜欢</span>
                <span>{{ data.commentCount === null ? '0' : data.commentCount }} 评论</span>
            </div>
        </div>
        <div class="author">
            <div class="left">
                <div class="img-div">
                    <a :href="'/user/' + data.userId">
                        <img :src="data.userAvatar === null ? '/defaultavatar.jpg' : data.userAvatar" />
                    </a>
                </div>
                <div class="author-info">
                    <div class="name"><a :href="'/user/' + data.userId">{{ data.userName }}</a></div>
                    <div class="info-info min-font">
                        <span>粉丝：{{ data.userFanCount }}</span>
                        <span>文章：{{ data.userArticleCount }}</span>
                    </div>
                </div>
            </div>
            <div class="right"></div>
        </div>
        <div class="div-line"></div>
        <div class="content">
            <v-md-editor :model-value="data.text" mode="preview"></v-md-editor>
        </div>
    </div>
</template>

<style scoped>
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
.on {
    color: #00AEEC;
}
.like:hover {
    color: #00AEEC;
}
.like {
    cursor: pointer;
}
</style>