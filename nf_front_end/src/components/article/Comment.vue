<script setup lang='ts'>
import { ref, reactive, onMounted, watch } from 'vue';
import { replyState, reply, action, flushedCommPro } from '../../store';
import { getCommentListB } from '../../api/commentapi';
import SonComment from './SonComment.vue';
import Reply from './Reply.vue';
let getData = defineProps<{
    data: {
        id: string,
        userId: string,
        userName: string,
        userAvatar: string,
        words: string,
        time: string,
        like: number,
        dislike: number,
        active: number, //-1未评价 0喜欢 1不喜欢
    },
}>();
const sonComments = ref([]);
const replyDiv = ref(false);
const replyData = reactive({
    id: '',
    ruserId: 'no2',
});
const page = reactive({
    id: '', // 评论id
    level: 1, // 0一级评论 1二级评论
    sort: 1, // 二级评论根据时间排序
    total: 0,
    size: 14,
    cur: 1,
});
function changPage(cur: number = 1) {
    getCommentListB(page, cur, page.size).then(res => {
        sonComments.value = res.data.list;
        page.total = res.data.total;
    });
}
watch(action, () => {
    if (replyState.reCreate) {
        if (replyState.commId === getData.data.id) {
            replyDiv.value = false;
            if (replyState.newCreate.commProId === '') {
                replyState.haveReply = true;
                replyState.commId = replyState.newCreate.commId;
                replyState.isCommPro = false;
                replyState.commProId = '';
                replyState.ruserId = replyState.newCreate.ruserId;
                replyState.reCreate = false;// 清空
                replyState.newCreate.commId = '';
                replyState.newCreate.commProId = '';
                replyState.newCreate.ruserId = '';
            } else {
                replyState.haveReply = true;
                replyState.commId = replyState.newCreate.commId;
                replyState.isCommPro = true;
                replyState.commProId = replyState.newCreate.commProId;
                replyState.ruserId = replyState.newCreate.ruserId;
                replyState.reCreate = false;
                replyState.newCreate.commId = '';
                replyState.newCreate.commProId = '';
                replyState.newCreate.ruserId = '';
            }
            action.value++;
        }
        return;
    }
    if (replyState.haveReply) {
        if (replyState.commId === getData.data.id) {
            replyDiv.value = true;
            replyData.ruserId = replyState.ruserId;
        }
    } else {
        if (replyState.commId === getData.data.id) {
            replyDiv.value = false;
            replyState.commId = ''; // 清空状态
            replyState.isCommPro = false;
            replyState.commProId = '';
            replyState.ruserId = '';
        }
    }
});
watch(flushedCommPro, () => {
    changPage(page.cur);
})
onMounted(() => {
    page.id = getData.data.id;
    replyData.id = getData.data.id;
    changPage();
});
</script>

<template>
    <div class="comment-body">
        <div class="avatar">
            <a :href="'/user/' + data.userId">
                <img :src="data.userAvatar === null ? '/defaultavatar.jpg' : data.userAvatar" />
            </a>
        </div>
        <div class="content">
            <div class="content-postion"></div>
            <div class="user-name">
                <a :href="'/user/' + data.userId">{{ data.userName }}</a>
            </div>
            <div class="comment-item">{{ data.words }}</div>
            <div class="comment-info">
                <span>{{ data.time }}</span>
                <span :class="data.active === 0 ? 'like on' : 'like'">赞：{{ data.like === null ? '0' : data.like }}</span>
                <span :class="data.active === 1 ? 'like on' : 'like'">踩：{{ data.dislike }}</span>
                <span class="replay-btn" :style="replyState.commId === getData.data.id && replyState.commProId === '' ? 'color: #00a1d6' : ''" @click="reply(data.id, 'no2')">回复</span>
            </div>
        </div>
        <div class="replay-body">
            <SonComment
                :data="item"
                v-for="(item, index) in sonComments">
            </SonComment>
            <div class="reply-div" v-if="replyDiv">
                <Reply :data="replyData" v-if="replyDiv"></Reply>
            </div>
            <el-pagination class="page-div" v-if="page.total > page.size"
                v-model:current-page="page.cur"
                v-model:page-size="page.size"
                layout="prev, pager, next"
                :total="page.total"
                @current-change="changPage"
            />
        </div>
    </div>
</template>

<style scoped>
.reply-div {
    margin-top: 15px;
}
.page-div {
    justify-content: center;
}
.comment-body {
    position: relative;
    border-top: 1px solid #e5e9ef;
    margin-bottom: 5px;
}
.avatar {
    width: 82px;
    height: 48px;
    position: absolute;
    margin-top: 24px;
    text-align: center;
}
.avatar img {
    width: 48px;
    height: 48px;
    border-radius: 50%;
}
.content {
    margin-left: 85px;
    margin-top: 24px;
}
.user-name a {
    font-size: 14px;
    font-weight: bold;
    color: #6d757a;
    text-decoration: none;
    transition: 0.1s;
}
.user-name a:hover {
    color: #00a1d6;
}
.comment-item {
    font-size: 14px;
    color: #000;
    margin: 5px 0;
}
.comment-info {
    font-size: 12px;
    color: #99a2aa;
    margin-top: 6px;
}
.comment-info span {
    margin-right: 50px;
}
.like {
    cursor: pointer;
}
.comment-info .on {
    color: #00a1d6;
}
.replay-btn:hover {
    color: #00a1d6;
    cursor: pointer;
}
.replay-body {
    margin-left: 85px;
}
</style>