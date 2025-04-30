import { mount, flushPromises } from '@vue/test-utils'
import LoginPage from '../src/views/LoginPage.vue';
import { describe, it, expect, vi, beforeEach } from 'vitest'
import axios from 'axios'
import { createMemoryHistory, createRouter } from 'vue-router'
import { routes } from "@/router/index.js"

vi.mock('axios')

describe('<LoginPage>', () => {

    let wrapper
    let router

  beforeEach(async() => {
          router = createRouter ({
              history: createMemoryHistory(),
              routes: routes
          })
          router.push("/");
          await router.isReady();
      });

      wrapper = mount(LoginPage, {
        global: {
          plugins: [router]
        }
      })
  
     

      it('renders email and password input and login button', () => {
        expect(wrapper.find('input[type="email"]').exists()).toBe(true)
        expect(wrapper.find('input[type="password"]').exists()).toBe(true)
        expect(wrapper.find('button[type="submit"]').exists()).toBe(true)
      });
    
      it('shows success message and redirects on successful login', async () => {
        // mock axios post
        axios.post.mockResolvedValue({
          status: 200,
          data: { token: 'mocked-jwt-token' }
        });

        // simulate form input
        await wrapper.find('input[type="email"]').setValue('test@example.com')
        await wrapper.find('input[type="password"]').setValue('password123')
        await wrapper.find('form').trigger('submit.prevent')

        await flushPromises()

        expect(localStorage.getItem('authToken')).toBe('mocked-jwt-token')
        expect(wrapper.text()).toContain('Login successful')
    })

    it('shows error message on failed login', async () => {
        axios.post.mockRejectedValue({ response: { status: 401 } })
    
        await wrapper.find('input[type="email"]').setValue('wrong@example.com')
        await wrapper.find('input[type="password"]').setValue('wrongpass')
        await wrapper.find('form').trigger('submit.prevent')
    
        await flushPromises()
    
        expect(wrapper.text()).toContain('Invalid credentials')
      })

});

