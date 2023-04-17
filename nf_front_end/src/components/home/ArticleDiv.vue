<script setup lang='ts'>
import { ref, onMounted, watch } from 'vue';
import { addLikeAB } from '../../api/likeaapi';
let getData = defineProps<{
    data: {
        id: string,
        title: string,
        text: string,
        cover: string,
        userId: string,
        userName: string,
        userAvatar: string,
        secondCateId: string,
        secondCateName: string,
        watch: number,
        like: number,
        commentCount: number,
        likeActive: boolean,
    },
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
    <div class="article-body">
        <div class="postion-div">
            <div class="article-left">
                <a :href="'/article/' + data.id" class="title">{{ data.title }}</a>
                <div class="article-desc-div">
                    <a :href="'/article/' + data.id" class="article-desc min-font">
                        {{ data.text }}
                    </a>
                </div>
                <div class="article-info">
                    <a class="avatar-name min-font" :href="'/user/' + data.userId">
                            <img :src="data.userAvatar === null ? '/defaultavatar.jpg' : data.userAvatar" />
                            {{ data.userName }}
                    </a>
                    <a class="ido min-font tag">{{ data.secondCateName }}</a>
                    <a class="ido min-font">观看：{{ data.watch }}</a>
                    <a @click="addLike(data.id)" v-if="data.likeActive" class="ido min-font like on">喜欢：{{ data.like === null ? '0' : data.like }}</a>
                    <a @click="addLike(data.id)" v-if="!data.likeActive" class="ido min-font like">喜欢：{{ data.like === null ? '0' : data.like }}</a>
                    <a class="ido min-font">评论：{{ data.commentCount === null ? '0' : data.commentCount }}</a>
                </div>
            </div>
            <div class="cover-img">
                <a :href="'/article/' + data.id">
                    <img :src="data.cover === null ? '/defaultcover.jpg' : data.cover" />
                </a>
            </div>
        </div>
    </div>
</template>

<style scoped>
.min-font {
    font-size: 12px;
    color: #99a2aa;
}
.article-body {
    padding-top: 20px;
    padding-bottom: 20px;
    border-bottom: 1px solid #f4f5f7;
}
.postion-div {
    display: flex;
}
.article-left {
    position: relative;
    width: 543px;
    margin-right: 20px;
}
.title {
    display: block;
    text-decoration: none;
    color: #000;
    font-size: 20px;
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    transition: 0.2s;
}
.title:hover {
    color: #00AEEC;
}
.article-desc-div {
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
    margin-top: 4px;
}
.article-desc {
    text-decoration: none;
}
.article-info {
    height: 24px;
    position: absolute;
    bottom: 0;
}

.avatar-name {
    text-decoration: none;
    margin-right: 22px;
}
.avatar-name:hover {
    color: #00AEEC;
}
.avatar-name img {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    vertical-align:middle
}
.ido {
    padding-right: 30px;
    text-decoration: none;
}
.tag:hover, .like:hover {
    color: #00AEEC;
    cursor: pointer;
}

.cover-img img {
    width: 117px;
    height: 88px;
    border-radius: 8px;
    object-fit: cover;
}
.on {
    color: #00AEEC;
}
</style>