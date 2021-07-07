package com.github.moaxcp.x11protocol

import org.gradle.api.Action
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPlugin
import org.gradle.api.tasks.SourceSetContainer

class X11ProtocolPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        project.extensions.create('x11Protocol', X11ProtocolExtension)
        def task = project.task(type: GenerateX11ProtocolTask, 'generateX11Protocol')
        task.xcbXmls = project.x11Protocol.xcbXmls
        task.exclude = project.x11Protocol.exclude
        task.outputSrc = project.x11Protocol.outputSrc
        task.outputResources = project.x11Protocol.outputResources
        task.keysymHeaders = project.x11Protocol.keysymHeaders

        project.getPlugins().withType(JavaPlugin.class, new Action<JavaPlugin>() {
            @Override
            void execute(final JavaPlugin plugin) {
                SourceSetContainer sourceSets = (SourceSetContainer) project.getProperties().get("sourceSets")
                sourceSets.getByName("main").getJava().srcDir(task.outputSrc)
                sourceSets.getByName("main").getResources().srcDir(task.outputResources)
                def compileTask = project.tasks.getByName(JavaPlugin.COMPILE_JAVA_TASK_NAME)
                compileTask.dependsOn(task);
            }
        })
    }
}
