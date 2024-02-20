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
            <a href="">            <b-avatar variant="info" src="https://lh3.googleusercontent.com/proxy/eyGO7e8XOSd3IWsnc-bH6a374xLp0qv4FYDHbEI4y9dRXOnsOhMI0njIuPe1lQJ9bXUbC6hgTrtMXlSGB0IE5bm7VkQITX6RN11Lfoc9MczbPjcuahRFt8TtpNrbYgX2vtNHyNoQGOL8dR_KP43kXj-Y" class="mr-3"></b-avatar>
            </a>
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