<script setup lang='ts'>
import { ref, reactive } from 'vue';
import { replyState, reply } from '../../store';
let getData = defineProps<{
    data: {
        id: string,
        fatherId: string,
        userId: string, // 评论创造者的id
        userAvatar: string, // 评论创造者的头像
        userName: string, // 评论创造者的name
        ruserId: string, // 接收者的id
        ruserName: string, // 接受者的name
        words: string,
        time: string,
        like: number,
        dislike: number,
        active: number, //-1未评价 0喜欢 1不喜欢
    },
}>();
</script>

<template>
    <div>
        <div class="postion-div">
            <a :href="'/user/' + data.userId">
                <img class="avatar" :src="data.userAvatar === null ? '/defaultavatar.jpg' : data.userAvatar" />
            </a>
            <div class="content">
                <a :href="'/user/' + data.userId">{{ data.userName }}</a>
                <a v-if="data.ruserId !== null" :href="'/user/' + data.ruserId">{{'回复 ' +  data.ruserName }}</a>
                <span>{{ data.words }}</span>
            </div>
            <div class="info">
                <span>{{ data.time }}</span>
                <span :class="data.active === 0 ? 'like on' : 'like'">赞：{{ data.like }}</span>
                <span :class="data.active === 1 ? 'like on' : 'like'">踩：{{ data.dislike }}</span>
                <span class="replay-btn" :style="replyState.commProId === getData.data.id ? 'color: #00a1d6' : ''" @click="reply(data.id, data.userId, data.fatherId)">回复</span>
            </div>
        </div>
    </div>
</template>

<style scoped>
.postion-div {
    padding: 10px 0;
}
.avatar {
    width: 24px;
    height: 24px;
    border-radius: 50%;
    position: absolute;
}
.content {
    margin-left: 30px;
}
.content a {
    text-decoration: none;
    color: #6d757a;
    font-size: 12px;
    font-weight: bold;
    margin-right: 10px;
}
.content a:hover {
    color: #00a1d6;
}
.content span {
    font-size: 14px;
}
.info {
    margin-left: 30px;
    font-size: 12px;
    color: #99a2aa;
    margin-top: 6px;
}
.info span {
    margin-right: 50px;
}
.like {
    cursor: pointer;
}
.info .on {
    color: #00a1d6;
}
.replay-btn:hover {
    color: #00a1d6;
    cursor: pointer;
}
</style>