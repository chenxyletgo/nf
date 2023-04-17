<script setup lang='ts'>
import { ElMessage } from 'element-plus';
import { ref, reactive, onMounted, watch } from 'vue';
import { getMessListB } from '../../api/messageapi';
const messages = ref();
const page = reactive({
    total: 0,
    cur: 1,
    size: 10,
});
function changPage(cur: number = 1) {
    getMessListB(cur, page.size).then(res => {
        messages.value = res.data.list;
        page.total = res.data.total;
    });
}
onMounted(() => {
    changPage();
});
</script>

<template>
    <div>
        <div class="content-div">
            <!-- 头部 -->
            <div class="head">
                系统消息
            </div>
            <!-- 内容 -->
            <div>
                <el-table :data="messages" style="width: 100%">
                    <el-table-column prop="text" label="内容" width="700">
                        <template #default="scope">
                            <span v-html="scope.row.text"></span>
						</template>
                    </el-table-column>
                    <el-table-column prop="modifyTime" label="时间" width="200" />
                </el-table>
            </div>
            <div>
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
.mess-div {
    height: 50px;
}
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

</style>