<script setup lang='ts'>
import { ref, reactive, onMounted, watch } from 'vue';
import { useRoute } from 'vue-router';
import { homePage, homePageWatch } from '../store';
import { firstCatesAddOther, secondCatesAddOther } from '../fun';
import { getArticleDivListB } from '../api/articleapi';
import { getFirstCatesB, getSecondCateB } from '../api/categoryapi';
import Carousel from '../components/home/carousel/Carousel.vue';
import SonCategory from '../components/home/SonCategory.vue';
import ArticleDiv from '../components/home/ArticleDiv.vue';
const route = useRoute();
const firstCates = ref();
const secondCates = ref([]);
const articleDivs = ref();
function getFirstCates() {
    getFirstCatesB().then(res => {
        firstCates.value = res.data.list;
        firstCatesAddOther(firstCates);
    });
}
function getSecondCates(pathName: string) {
    getSecondCateB(pathName).then(res => {
        secondCates.value = res.data.list;
        secondCatesAddOther(secondCates);
    });
}
function getCurFirstCate() {
    if (route.params && route.params.category) homePage.pathName = route.params.category as string;
}
function changeSecondCates() {
    if (homePage.pathName !== 'recommend' && homePage.pathName !== 'other') getSecondCates(homePage.pathName);
    if (homePage.pathName === 'other') secondCates.value = [{ id: '', name: '全部'} as never];
}
function resetHomePage() {
    homePageWatch.secondCateId = '';
    homePageWatch.sort = 0;
}
function changPage(cur: number = 1) {
    getArticleDivListB(Object.assign(homePage, homePageWatch), cur, homePage.size).then(res => {
        articleDivs.value = res.data.list;
        homePage.total  = res.data.total;
    });
}
watch(route, () => {
    secondCates.value = [];
    getCurFirstCate();
    changeSecondCates();
    resetHomePage();
    changPage();
});
watch(homePageWatch, () => {
    changPage();
});
onMounted(() => {
    getFirstCates();
    getCurFirstCate();
    changeSecondCates();
    changPage();
});
</script>

<template>
    <div class="div-2">
        <div class="div-1">
            <el-affix :offset="70">
                <div class="left-list">
                    <router-link
                        v-for="item in firstCates"
                        :to="'/home/' + item.pathName"
                    >
                        <span>{{ item.name }}</span>
                    </router-link>
                </div>
            </el-affix>
        </div>
        <div class="div-5">
            <div class="div-3">
                <div v-if="homePage.pathName === 'recommend'">
                    <Carousel></Carousel>
                    <div class="div-4">
                        <label>推荐文章</label>
                    </div>
                </div>
                <div v-if="homePage.pathName !== 'recommend'">
                    <SonCategory :data="secondCates"></SonCategory>
                </div>
                <div>
                    <ArticleDiv :key="new Date().getTime() + index" :data="item" v-for="(item, index) in articleDivs"></ArticleDiv>
                </div>
                <el-pagination class="page-div"  v-if="homePage.total > homePage.size"
                    v-model:current-page="homePage.cur"
                    v-model:page-size="homePage.size"
                    layout="prev, pager, next, jumper"
                    :total="homePage.total"
                    :background="true"
                    @current-change="changPage"
                />
            </div>
        </div>
    </div>
</template>

<style scoped>
.div-5 {
    /* width: 1160px; */
    background-color: white;
    border-bottom-left-radius: 10px;
    border-bottom-right-radius: 10px;
    margin-top: 20px;
}
.page-div {
    justify-content: center;
    margin-top: 10px;
    margin-bottom: 70px;
}
.div-3 {
    width: 700px;
    margin-top: 13px;
    margin-left: 20px;
    margin-bottom: 20px;
}
.div-4 {
    height: 25px;
    font-size: 18px;
}
.div-2 {
    width: 1160px;
    margin: 0 auto;
    display: flex;
}
.div-1 {
    width: 180px;
}
.left-list {
    padding-bottom: 20px;
    border-radius: 8px;
    margin-right: 15px;
    border: 1px solid white;
    background-color: white;
}
.left-list a {
    display: block;
    width: 110px;
    line-height: 30px;
    margin: 20px auto auto auto;
    text-align: center;
    text-decoration: none;
    color: black;
    transition: 0.2s;
}
.left-list a:hover {
    color: #00AEEC;
}
.router-link-active span {
    color: #00AEEC;
}
</style>