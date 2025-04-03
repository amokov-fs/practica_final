<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div>
    
    <div id="nav" style = "display:flex;gap:10px;">
      <v-btn color="#028CF5">
        <RouterLink to="/" style="text-decoration: none; ">
          <span class = "text-white" >
            Home
          </span>
        </RouterLink>
      </v-btn>
      <v-btn color="#028CF5">
        <RouterLink to="/employees" style="text-decoration: none; ">
          <span class = "text-white" >
            Empleados
          </span>
        </RouterLink>
      </v-btn>
      <v-btn color="#028CF5">
        <RouterLink to="/projects" style="text-decoration: none; ">
          <span class = "text-white" >
            Proyectos
          </span>
        </RouterLink>
      </v-btn>
    </div>
    
    
    <RouterView />
  </div>

  

  
</template>

<style>

body {
  padding: 10px;
}

</style>
