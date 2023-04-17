import { defineConfig } from 'vite';
import vue from '@vitejs/plugin-vue';
import AutoImport from 'unplugin-auto-import/vite'; // element plus 自动按需导入依赖
import Components from 'unplugin-vue-components/vite'; // element plus 自动按需导入依赖
import { ElementPlusResolver } from 'unplugin-vue-components/resolvers'; // element plus 自动按需导入依赖

// https://vitejs.dev/config/
export default defineConfig({
	plugins: [
		vue(),
		AutoImport({
			resolvers: [ElementPlusResolver()],
		}),
		Components({
			resolvers: [ElementPlusResolver()],
		})
	],
});
