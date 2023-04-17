<script setup lang='ts'>
import { ElMessage } from 'element-plus';
import { ref, reactive, onMounted, watch } from 'vue';
import { useRouter } from 'vue-router';
import { getArticleListByUserIdB, delArticleB } from '../../api/articleapi';
const states = [{name: '全部', value: -1}, {name: '草稿', value: 0}, {name: '审核中', value: 1}, {name: '已发布', value: 2}, {name: '打回', value: 3}];
const data = ref();
const page = reactive({
    total: 0,
    cur: 1,
    size: 14,
});
const pageWatch = ref(-1);
function changPage(cur: number = 1) {
    getArticleListByUserIdB(pageWatch.value, cur, page.size).then(res => {
        data.value = res.data.list;
        page.total = res.data.total;
    });
}
function delAricle(id: string) {
    if (page.total % page.size === 1 && Math.floor(page.total / page.size) !== 0) {
        delArticleB(id).then(res => {
            ElMessage.success((res as any).message);
            page.cur--;
            changPage(page.cur);
        });
    } else {
        delArticleB(id).then(res => {
            ElMessage.success((res as any).message);
            changPage(page.cur);
        });
    }
}
function edit(id: string) {
    window.location.href = '/editarticle/' + id;
}
function preview(id: string) {
    window.open('/preview/' + id);
}
function goArticle(id: string) {
    window.open('/article/' + id);
}
watch(pageWatch, () => {
    changPage();
});
onMounted(() => {
    changPage();
});
</script>

<template>
    <div>
        <div class="content-div">
            <!-- 头部 -->
            <div class="head">
                文章管理
                <div class="select-div">
                    <el-select v-model="pageWatch" class="m-2" placeholder="Select">
                        <el-option
                            v-for="item in states"
                            :key="item.value"
                            :label="item.name"
                            :value="item.value"
                        />
                    </el-select>
                </div>
            </div>
            <!-- 内容 -->
            <div>
                <el-table :data="data" style="width: 100%">
                    <el-table-column :show-overflow-tooltip="true" prop="title" label="标题" width="150" />
                    <el-table-column prop="state" label="状态" width="100">
                        <template #default="scope">
							<span v-if="scope.row.state === 0">草稿</span>
                            <span v-if="scope.row.state === 1">审核中</span>
                            <span v-if="scope.row.state === 2">已发布</span>
                            <span v-if="scope.row.state === 3">打回</span>
						</template>
                    </el-table-column>
                    <el-table-column prop="watch" label="观看数" width="100">
                        <template #default="scope">
							<span>{{ scope.row.watch === null ? '0' : scope.row.watch }}</span>
						</template>
                    </el-table-column>
					<el-table-column prop="like" label="喜欢数" width="100">
                        <template #default="scope">
							<span>{{ scope.row.like === null ? '0' : scope.row.commentCount }}</span>
						</template>
                    </el-table-column>
					<el-table-column prop="commentCount" label="评论数" width="100">
                        <template #default="scope">
							<span>{{ scope.row.commentCount === null ? '0' : scope.row.commentCount }}</span>
						</template>
                    </el-table-column>
					<el-table-column prop="time" label="发布时间" width="180" />
                    <el-table-column prop="id" fixed="right" label="操作" width="200">
						<template #default="scope">
                            <el-button @click="goArticle(scope.row.id)" link type="primary" :disabled="scope.row.state !== 2">查看</el-button>
							<el-button @click="preview(scope.row.id)" link type="primary" :disabled="scope.row.state === 2">预览</el-button>
							<el-button @click="edit(scope.row.id)" link type="primary" :disabled="scope.row.state === 1">编辑</el-button>
							<el-button @click="delAricle(scope.row.id)" link type="primary">删除</el-button>
						</template>
                    </el-table-column>
                </el-table>
                <el-pagination class="page-div"
                    v-model:current-page="page.cur"
                    v-model:page-size="page.size"
                    layout="prev, pager, next, jumper"
                    :total="page.total"
                    :background="true"
                    @current-change="changPage"
                />
            </div>
        </div>
    </div>
</template>

<style scoped>
.page-div {
    justify-content: center;
    margin-top: 10px;
    margin-bottom: 70px;
}
.content-div .head {
    height: 48px;
    line-height: 48px;
    font-size: 18px;
    font-weight: 600;
    color: #2e2e2e;
    padding-left: 15px;
    border-bottom: 1px solid #f0f0f2;
    position: relative;
    display: flex;
}
.content-div {
    background-color: #fff;
    margin-left: 15px;
    border: 1px solid #dcdfe6;
    border-top: 0px;
    border-bottom: 0px;
}
.select-div {
    position: absolute;
    right: 5px;
}
</style>