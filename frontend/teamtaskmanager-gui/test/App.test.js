import { flushPromises, mount } from "@vue/test-utils";
import { describe, it, expect, beforeEach} from "vitest";
import { routes } from "@/router/index.js"
import App from '../src/App.vue';
import { createMemoryHistory, createRouter } from "vue-router";

describe('<App>', () => {

    let router;

    beforeEach(async() => {
        router = createRouter ({
            history: createMemoryHistory(),
            routes: routes
        })
        router.push("/");
        await router.isReady();
    });

    it('should render the correct HTML', async () => {
        const wrapper = mount(App, {
            global: {
                plugins: [router]
            }

        });
        // We see that the router-link-active is on the / which is normal because we pushed / on the router in the beforeach test
        expect(wrapper.html()).toMatchInlineSnapshot(`
          "<div data-v-7a7a37b1="">
            <nav data-v-7a7a37b1="" class="bg-gray-800 text-white p-4 flex justify-between">
              <div data-v-7a7a37b1="" class="font-bold text-xl">TeamTaskManager</div>
              <div data-v-7a7a37b1="" class="space-x-4"><a data-v-7a7a37b1="" aria-current="page" href="/" class="router-link-active router-link-exact-active hover:underline">Home</a><a data-v-7a7a37b1="" href="/register" class="hover:underline">Register</a><a data-v-7a7a37b1="" href="/login" class="hover:underline">Login</a></div>
            </nav>
            <div data-v-7a7a37b1="" class="p-10 text-center">
              <h1 class="text-4xl font-bold mb-4">Welcome to TeamTaskManager</h1>
              <p class="text-lg text-gray-600 mb-6"> A simple, collaborative task board — just like Trello, but tailored for your team. </p><a href="/register" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded"> Get Started </a>
            </div>
          </div>"
        `);
        
        router.push('/login')
        await router.isReady();
        await flushPromises();
        // Now testing that the router-link-active is on the /login
        expect(wrapper.html()).toMatchInlineSnapshot(`
          "<div data-v-7a7a37b1="">
            <nav data-v-7a7a37b1="" class="bg-gray-800 text-white p-4 flex justify-between">
              <div data-v-7a7a37b1="" class="font-bold text-xl">TeamTaskManager</div>
              <div data-v-7a7a37b1="" class="space-x-4"><a data-v-7a7a37b1="" href="/" class="hover:underline">Home</a><a data-v-7a7a37b1="" href="/register" class="hover:underline">Register</a><a data-v-7a7a37b1="" href="/login" class="router-link-active router-link-exact-active hover:underline" aria-current="page">Login</a></div>
            </nav>
            <div data-v-67e64f9f="" data-v-7a7a37b1="" class="max-w-md mx-auto mt-10 p-6 bg-white shadow-lg rounded-xl">
              <h2 data-v-67e64f9f="" class="text-2xl font-bold mb-6 text-center">Login</h2>
              <form data-v-67e64f9f="" class="space-y-4">
                <div data-v-67e64f9f=""><label data-v-67e64f9f="" class="block mb-1">Email</label><input data-v-67e64f9f="" type="email" required="" class="w-full border px-3 py-2 rounded"></div>
                <div data-v-67e64f9f=""><label data-v-67e64f9f="" class="block mb-1">Password</label><input data-v-67e64f9f="" type="password" required="" class="w-full border px-3 py-2 rounded"></div><button data-v-67e64f9f="" type="submit" class="w-full bg-green-600 hover:bg-green-700 text-white font-semibold py-2 px-4 rounded"> Login </button>
              </form>
              <p data-v-67e64f9f="" class="mt-4 text-red-600"></p>
            </div>
          </div>"
        `);
    });
});