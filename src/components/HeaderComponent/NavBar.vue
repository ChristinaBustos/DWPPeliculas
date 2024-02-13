<template>
  <div>
    <div>
      <b-navbar toggleable="lg" type="dark" class="navbar">
        <b-navbar-brand to="inicio">CrisPelis</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item to="peliculas">Peliculas</b-nav-item>
      
          </b-navbar-nav>

          <b-navbar-nav class="ml-auto">
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
    </div>
  </div>
</template>

<script>
export default {
  name: "NavBar",
};
</script>

<style scoped>
.navbar{
    background-color: #083565;
}
</style>