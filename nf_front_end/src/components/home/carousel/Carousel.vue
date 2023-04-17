<script setup lang='ts'>
import { ref, onMounted, onUnmounted, watch, onUpdated } from 'vue';
import { carouselFun, timer, changeImg } from './Carousel';
import { getTopArticleListB } from '../../../api/articleapi';
const data = ref();
function getData() {
    getTopArticleListB().then(res => {
        data.value = res.data.list;
    });
}
// DOM节点加载完成
onMounted(() => {
    getData();
});
onUpdated(() => {
    if (data.value.length === 0) {
        return;
    }
    carouselFun();
});
// 组件销毁时销毁定时器
onUnmounted(() => {
    clearInterval(timer);
})
</script>

<template>
    <div class="carousel-body">
        <div class="postion-div">
            <ul class="carousel-list">
                <li v-for="item in data">
                    <a :href="'/article/' + item.id">
                        <img :src="item.cover" />
                    </a>
                </li>
            </ul>
            <div class="titles" ref="titlesRef">
                <a v-for="item in data" :href="'/article/' + item.id">{{ item.title }}</a>
            </div>
            <ul class="indicator">
                <li v-for="(item, index) in data">
                    <div class="indicator-item" @click="changeImg(index)"></div>
                </li>
            </ul>
        </div>
    </div>
</template>

<style scoped>
.carousel-body {
    width: 680px;
    height: 212px;
    margin-bottom: 25px;
}
.postion-div {
    width: 680px;
    height: 212px;
    border-radius: 8px;
    position: absolute;
    overflow: hidden;
}
.carousel-list {
    position: absolute;
    transition: 0.2s;
    display: flex;
}
.carousel-list, .indicator {
    list-style: none;
    margin: 0;
    padding: 0;
}
.carousel-list img {
    width: 680px;
    height: 212px;
}
.titles {
    width: 100%;
    height: 76px;
    position: absolute;
    bottom: 0;
    background-image: linear-gradient(-180deg,transparent 42%,rgba(0,0,0,.5));
}
.titles a {
    width: 100%;
    position: absolute;
    bottom: 0;
    padding: 40px 10px 5px 10px;
    text-decoration: none;
    color: #fff;
    visibility: hidden;
}
/* 动态样式 */
.titles .on {
    visibility: visible;
}
.indicator {
    position: absolute;
    bottom: 8px;
    right: 15px;
    display: flex;
}
.indicator li {
    width: 20px;
    height: 20px;
    margin-left: 5px;
    display: flex;
}
/* 动态样式 */
.indicator li .indicator-item {
    width: 10px;
    height: 10px;
    background-color: #fff;
    border-radius: 50%;
    margin: auto;
}
.indicator li .indicator-item:hover {
    border: 1px solid #fff;
    background-color: #00AEEC;
    cursor: pointer;
}
/* 动态样式 */
.indicator li .on {
    width: 20px;
    height: 20px;
    background-image: url(/dot.png);
}
.indicator li .on:hover {
    cursor: pointer;
}
</style>