import { createApp } from 'vue'
import './style.css'
import App from './App.vue'
import router from './router';
import 'element-plus/theme-chalk/index.css';
import VueMarkdownEditor from '@kangc/v-md-editor';
import '@kangc/v-md-editor/lib/style/base-editor.css';
import vuepressTheme from '@kangc/v-md-editor/lib/theme/vuepress.js';
import '@kangc/v-md-editor/lib/theme/style/vuepress.css';
import Prism from 'prismjs';
import 'prismjs/components/prism-java';
import createLineNumbertPlugin from '@kangc/v-md-editor/lib/plugins/line-number/index';

VueMarkdownEditor.use(vuepressTheme, { Prism, });
VueMarkdownEditor.use(createLineNumbertPlugin());

const app = createApp(App);
app.use(router);
app.use(VueMarkdownEditor);
app.mount('#app');
