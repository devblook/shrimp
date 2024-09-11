package team.devblook.shrimp;

import io.papermc.paper.plugin.loader.PluginClasspathBuilder;
import io.papermc.paper.plugin.loader.PluginLoader;
import io.papermc.paper.plugin.loader.library.impl.MavenLibraryResolver;
import org.eclipse.aether.artifact.DefaultArtifact;
import org.eclipse.aether.graph.Dependency;
import org.eclipse.aether.repository.RemoteRepository;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"unused", "UnstableApiUsage"})
public class ShrimpPluginLoader implements PluginLoader {

  @Override
  public void classloader(@NotNull final PluginClasspathBuilder classpathBuilder) {
    final MavenLibraryResolver resolver = new MavenLibraryResolver();

    final RemoteRepository unnamedRepository = new RemoteRepository.Builder(
      "unnamed-public",
      "default",
      "https://repo.unnamed.team/repository/unnamed-public/").build();

    resolver.addRepository(new RemoteRepository.Builder(
      "central",
      "default",
      "https://repo1.maven.org/maven2/").build());

    final Dependency inject = new Dependency(new DefaultArtifact("team.unnamed:inject:2.0.0"), null);
    final Dependency command = new Dependency(
      new DefaultArtifact("team.unnamed:commandflow-bukkit-commandmap:0.7.0"),
      null);
    final Dependency lombok = new Dependency(new DefaultArtifact("org.projectlombok:lombok:1.18.34"), null);
    final Dependency hikari = new Dependency(new DefaultArtifact("com.zaxxer:HikariCP:5.1.0"), null);
    final Dependency mongo = new Dependency(new DefaultArtifact("org.mongodb:mongodb-driver-sync:5.1.2"), null);

    resolver.addRepository(unnamedRepository);

    resolver.addDependency(inject);
    resolver.addDependency(command);
    resolver.addDependency(lombok);
    resolver.addDependency(hikari);
    resolver.addDependency(mongo);

    classpathBuilder.addLibrary(resolver);
  }
}
