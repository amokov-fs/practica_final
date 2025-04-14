<script setup>
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <div>
    <v-app style="background-color:#d4f1ff;">
      <v-main class="success">
        <v-container fluid>        
          <v-navigation-drawer :width="180" style="background-color:#028CF5;color:white;">
            <v-list-item>
              <img src="./assets/Logo_blanco_Future_Space_sin_fondo.png" alt="" style="width:150px;">
            </v-list-item>
            <v-divider></v-divider>
            <v-list-item link title="Inicio" href = "#/" ></v-list-item>
            <v-list-item link title="Empleados" href = "#/employees"></v-list-item>
            <v-list-item link title="Proyectos" href = "#/projects"></v-list-item>
            <v-list-item link title="Asignación" href = "#/employees_to_projects"></v-list-item>
          </v-navigation-drawer>
          <RouterView />
        
        </v-container>
      </v-main>
    </v-app>
  </div>

  

  
</template>

<style>





</style>
