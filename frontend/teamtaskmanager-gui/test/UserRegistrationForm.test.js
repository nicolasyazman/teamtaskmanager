import { mount } from "@vue/test-utils";
import UserRegistrationForm from '../src/views/UserRegistrationForm.vue';
import { flushPromises, mount } from "@vue/test-utils";
import { describe, it, expect, beforeEach, vi} from "vitest";
import { routes } from "@/router/index.js"
import { createMemoryHistory, createRouter } from "vue-router";
import axios from 'axios'

vi.mock('axios')

describe('<UserRegistrationForm>', () => {

    let router;

    beforeEach(async() => {
        router = createRouter ({
            history: createMemoryHistory(),
            routes: routes
        })
        router.push("/");
        await router.isReady();
    });

    const wrapper = mount(UserRegistrationForm, {
        plugins: [router]
    });

    it('should render the correct HTML', () => {

        expect(wrapper.html()).toMatchInlineSnapshot();
    });

    it('should show success message on correct registration', async () => {

        axios.post.mockResolvedValue({
            status: 200,

        })

        // First let's check we can find the registration buttons
        expect(wrapper.find("#registrationform-username").exists()).toBe(true);
        expect(wrapper.find("#registrationform-password").exists()).toBe(true);
        expect(wrapper.find("#registrationform-email").exists()).toBe(true);

        // simulate form input
        await wrapper.find('#registrationform-username').setValue('MySuperUser')
        await wrapper.find('#registrationform-email').setValue('test@example.com')
        await wrapper.find('#registrationform-password').setValue('password123')
        await wrapper.find('form').trigger('submit.prevent')
        
        flushPromises();

        expect(wrapper.text()).toContain('User created successfully!');
    });

    it('should show undefined error message on incorrect registration', async () => {

        axios.post.mockRejectedValue({ response: { status: 401 } })

        // simulate form input
        await wrapper.find('#registrationform-username').setValue('MySuperUser')
        await wrapper.find('#registrationform-email').setValue('test@example.com')
        await wrapper.find('#registrationform-password').setValue('')
        await wrapper.find('form').trigger('submit.prevent')
        
        flushPromises();

        expect(wrapper.text()).toContain('Error: undefined');
    });

    it('should show specific error message on incorrect registration with data', async () => {

        axios.post.mockRejectedValue({ response: { status: 401,
            data: {
                message : 'user already exists' }
        },
        });

        // simulate form input
        await wrapper.find('#registrationform-username').setValue('MySuperUser')
        await wrapper.find('#registrationform-email').setValue('test@example.com')
        await wrapper.find('#registrationform-password').setValue('')
        await wrapper.find('form').trigger('submit.prevent')
        
        flushPromises();

        expect(wrapper.text()).toContain('Error: user already exists');
    });


});