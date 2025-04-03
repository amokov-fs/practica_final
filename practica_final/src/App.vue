<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div>
    <v-app>
      <v-container>
        
        <v-main class="success">
          <v-navigation-drawer :width="180" style="background-color:#028CF5;color:white;">
            <v-list-item title="Future Space"></v-list-item>
            <v-divider></v-divider>
            <v-list-item link title="Inicio" href = "#/" ></v-list-item>
            <v-list-item link title="Empleados" href = "#/employees"></v-list-item>
            <v-list-item link title="Proyectos" href = "#/projects"></v-list-item>
          </v-navigation-drawer>
          <RouterView />
        </v-main>
      </v-container>
    </v-app>
  </div>

  

  
</template>

<style>

.v-main {
  margin-left: -50px;
  margin-top: -10px;
}

</style>
