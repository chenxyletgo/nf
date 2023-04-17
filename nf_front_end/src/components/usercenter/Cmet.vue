<script setup lang='ts'>
import { ElMessage } from 'element-plus';
import { ref, reactive, onMounted, watch } from 'vue';
import { getAllArticleByUserIdB } from '../../api/articleapi';
import { getCommListByArticleIdB, delCommentB } from '../../api/commentapi';
const articles = ref();
const data = ref();
const page = reactive({
    total: 0,
    cur: 1,
    size: 14,
});
const pageWatch = ref(); // 文章id
function changPage(cur: number = 1) {
    getCommListByArticleIdB(pageWatch.value, cur, page.size).then(res => {
        data.value = res.data.list;
        page.total = res.data.total;
    });
}
function getArticles() {
    getAllArticleByUserIdB().then(res => {
        articles.value = res.data.list;
    });
}
function delComm(id: string) {
    if (page.total % page.size === 1 && Math.floor(page.total / page.size) !== 0) {
        delCommentB(id).then(res => {
            ElMessage.success((res as any).message);
            page.cur--;
            changPage(page.cur);
        });
    } else {
        delCommentB(id).then(res => {
            ElMessage.success((res as any).message);
            changPage(page.cur);
        });
    }
}
watch(pageWatch, () => {
    changPage();
});
onMounted(() => {
    getArticles();
});
</script>

<template>
    <div>
        <div class="content-div">
            <!-- 头部 -->
            <div class="head">
                评论管理
                <div class="select-div">
                    <el-select v-model="pageWatch" class="m-2" placeholder="选择文章">
                        <el-option
                            v-for="item in articles"
                            :key="item.id"
                            :label="item.title"
                            :value="item.id"
                        />
                    </el-select>
                </div>
            </div>
            <!-- 内容 -->
            <div>
                <el-table :data="data" style="width: 100%">
                    <el-table-column type="index" label="编号" width="60" />
                    <el-table-column :show-overflow-tooltip="true" prop="words" label="标题" width="150" />
                    <el-table-column prop="userName" label="发言人" width="100">
                        <template #default="scope">
                            <span>{{ scope.row.userName }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="level" label="级别" width="100">
                        <template #default="scope">
                            <span>{{ scope.row.level === 0 ? '一级' : '二级' }}</span>
                        </template>
                    </el-table-column>
                    <el-table-column prop="like" label="赞" width="100" />
                    <el-table-column prop="dislike" label="踩" width="100" />
					<el-table-column prop="time" label="发布时间" width="180" />
                    <el-table-column prop="id" fixed="right" label="操作" width="110">
						<template #default="scope">
                            <el-button link type="primary">查看</el-button>
							<el-button @click="delComm(scope.row.id)" link type="primary">删除</el-button>
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
    display: flex;
}
</style>