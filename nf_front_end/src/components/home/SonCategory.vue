<script setup lang='ts'>
import { ref, reactive, onMounted } from 'vue';
import { homePage, homePageWatch } from '../../store';
interface SonCategory {
    id: string,
    name: string,
}
let getData = defineProps<{
    data: SonCategory[],
}>();
function ListenerSortLabel() {
    let div = document.getElementsByClassName('sort-list')[0] as HTMLElement
    let btn = document.getElementsByClassName('sort-btn-label')[0] as HTMLElement
    btn.addEventListener('click', (event) => {
        div.style.display = 'block'
        event.stopPropagation()
    });
    document.addEventListener('click', () => {
        div.style.display = 'none'
    });
    div.addEventListener('click', (event) => {
        event.stopPropagation();
        div.style.display = 'none';
    });
}
onMounted(() => {
    ListenerSortLabel();
});
</script>

<template>
    <div class="categories-body">
        <a v-for="item in data" 
            :class="homePageWatch.secondCateId === item.id ? 'on' : ''"
            @click="homePageWatch.secondCateId = item.id">
            <span>{{ item.name }}</span>
        </a>
    </div>
    <div class="sort-body">
        <div class="sort-btn min-font">
            <label class="sort-btn-label">
                <span v-if="homePageWatch.sort === 0">默认排序</span>
                <span v-if="homePageWatch.sort === 1">发布时间排序</span>
                <span v-if="homePageWatch.sort === 2">观看数最多</span>
                <span v-if="homePageWatch.sort === 3">喜欢数最多</span>
                <span v-if="homePageWatch.sort === 4">评论数最多</span>
                <img src="/character.png" />
            </label>
            <ul class="sort-list">
                <li @click="homePageWatch.sort = 0">默认排序</li>
                <li @click="homePageWatch.sort = 1">发布时间排序</li>
                <li @click="homePageWatch.sort = 2">观看数最多</li>
                <li @click="homePageWatch.sort = 3">喜欢数最多</li>
                <li @click="homePageWatch.sort = 4">评论数最多</li>
            </ul>
        </div>
    </div>
</template>

<style scoped>
.categories-body a {
    cursor: pointer;
}
.min-font {
    font-size: 14px;
}
.sort-btn {
    position: relative;
}
.sort-btn-label:hover {
    cursor: pointer;
}
.sort-list {
    position: absolute;
    background-color: #fff;
    list-style: none;
    margin: 0;
    padding: 0;
    z-index: 1;
    box-shadow: 0 0 5px rgb(0 0 0 / 20%);
    margin-top: 10px;
    margin-left: -15px;
    display: none;
}
.sort-list li {
    height: 36px;
    line-height: 36px;
    padding-left: 15px;
    padding-right: 15px;
    transition: 0.2s;
}
.sort-list li:hover {
    color: #00AEEC;
    cursor: pointer;
}



.categories-body {
    margin-bottom: 22px;
    border-bottom: 1px solid #f4f5f7;
}
.categories-body a {
    display: inline-block;
    padding: 5px 10px 10px;
    margin-right: 20px;
    text-decoration: none;
    color: #000;
    font-size: 12px;
    transition: 0.2s;
}
.categories-body a:hover {
    color: #00AEEC;
}
.on {
    border-bottom: 1px solid #00AEEC;
}
.on span {
    color: #00AEEC;
}
</style>