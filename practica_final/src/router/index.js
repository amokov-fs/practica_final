import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import EmployeesView from '../views/EmployeesView.vue'
import ProjectsView from '../views/ProjectsView.vue'
import EmployeesToProjectsView from '../views/EmployeesToProjectsView.vue'

const router = createRouter({
  history: createWebHashHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
    },
    {
      path: '/employees',
      name: 'employees',
      component: EmployeesView,
    },
    {
      path: '/projects',
      name: 'projects',
      component: ProjectsView,
    },
    {
      path: '/employees_to_projects',
      name: 'employees_to_projects',
      component: EmployeesToProjectsView,
    },
  ],
})

export default router
