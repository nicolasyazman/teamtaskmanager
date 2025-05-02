import { mount, flushPromises } from '@vue/test-utils'
import Dashboard from '../src/views/Dashboard.vue'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import axios from 'axios'
import MockAdapter from 'axios-mock-adapter'
import { createRouter, createWebHistory } from 'vue-router'

const mock = new MockAdapter(axios)

const router = createRouter({
  history: createWebHistory(),
  routes: [{ path: '/login', name: 'Login', component: { template: '<div>Login</div>' } }],
})

beforeEach(() => {
  mock.reset()
  localStorage.clear()
})

describe('Dashboard.vue', () => {
  it('displays fetched projects', async () => {
    localStorage.setItem('authToken', 'mock-token')

    mock.onGet('http://localhost:8080/project/mine').reply(200, [
      { id: 1, name: 'Test Project 1', description: 'First project' },
      { id: 2, name: 'Test Project 2', description: 'Second project' }
    ])

    router.push('/')
    await router.isReady()

    const wrapper = mount(Dashboard, {
      global: {
        plugins: [router]
      }
    })

    await flushPromises()

    expect(wrapper.text()).toContain('Test Project 1')
    expect(wrapper.text()).toContain('Test Project 2')
  })

  it('shows message if no projects exist', async () => {
    localStorage.setItem('authToken', 'mock-token')
    mock.onGet('http://localhost:8080/project/mine').reply(200, [])

    router.push('/')
    await router.isReady()

    const wrapper = mount(Dashboard, {
      global: {
        plugins: [router]
      }
    })

    await flushPromises()

    expect(wrapper.text()).toContain('You have no projects yet.')
  })

  it('redirects to login if no token', async () => {
    const pushSpy = vi.spyOn(router, 'push')

    router.push('/')
    await router.isReady()

    const wrapper = mount(Dashboard, {
      global: {
        plugins: [router]
      }
    })

    await flushPromises()

    expect(pushSpy).toHaveBeenCalledWith('/login')
  })

  it('logs out and clears localStorage', async () => {
    localStorage.setItem('authToken', 'mock-token')

    const wrapper = mount(Dashboard, {
      global: {
        plugins: [router]
      }
    })

    const logoutBtn = wrapper.get('button')
    await logoutBtn.trigger('click')

    expect(localStorage.getItem('authToken')).toBeNull()
  })
})
