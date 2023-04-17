<script setup lang='ts'>
import { ref, reactive } from 'vue';
import { user, flushedComm, flushedCommPro } from '../../store';
import { addCommentB } from '../../api/commentapi';
import { ElMessage } from 'element-plus';
let getData = defineProps<{
    data: {
        id: string, // reply_id的值
        ruserId: string, // no1一级 no2二级 id三级
    }
}>();
const words = ref();
function submit() {
    let tmp = {words: words.value};
    addCommentB(Object.assign(getData.data, tmp)).then(res => {
        ElMessage.success((res as any).message);
        words.value = '';
        if (getData.data.ruserId === 'no1') flushedComm.value++;
        else flushedCommPro.value++;
    });
}
</script>

<template>
    <div class="reply-body">
        <div class="postion-div">
            <div class="user-face">
                <img :src="user.avatar === null || user.avatar === '' ? '/defaultavatar.jpg' : user.avatar" />
            </div>
            <div class="text-div">
                <textarea class="text-area" v-model="words" cols="80" rows="5" placeholder="发一条评论">
                </textarea>
                <div class="button-div">
                    <button type="submit" @click="submit">发表评论</button>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.postion-div {
    position: relative;
}
.user-face {
    width: 82px;
    height: 65px;
    text-align: center;
    position: absolute;
}
.user-face img {
    width: 48px;
    height: 48px;
    border-radius: 50%;
    margin-top: 8px;
}
.text-area {
    width: 85%;
    height: 60px;
    font-size: 12px;
    border: 1px solid #e5e9ef;
    color: #555;
    resize: none;
    border-radius: 4px;
    background-color: #f4f5f7;
    font-family: inherit;
    outline: #00a1d6;
    padding: 5px 10px;
    position: relative;
}
.text-area:hover {
    background-color: #fff;
    border: 1px solid #00a1d6;
}
.text-div .button-div {
    position: absolute;
    top: 0;
    right: 0;
}
.text-div button {
    width: 70px;
    height: 70px;
    padding: 4px 15px;
    background-color: #00a1d6;
    color: #fff;
    font-size: 14px;
    border: 1px solid #00a1d6;
    transition: 0.2s;
    border-radius: 4px;
    cursor: pointer;
}
.text-div button:hover {
    background-color: #00AEEC;
    border: 1px solid #00AEEC;

}
.text-div {
    margin-left: 85px;
}
</style>