import jenkins.model.*

def jobName = 'SimpleJobWithBuildScript'
def jobDescription = 'A pipeline job that fetches and runs build.sh from a repository.'
def gitRepoUrl = 'https://github.com/your-repo/your-project.git'
def branch = 'main'

def jenkins = Jenkins.instance

if (jenkins.getItem(jobName) != null) {
    println "Job ${jobName} already exists!"
    return
}

// Create a pipeline job
def job = jenkins.createProject(org.jenkinsci.plugins.workflow.job.WorkflowJob, jobName)
job.description = jobDescription

// Define the pipeline script
def pipelineScript = """
pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git branch: '${branch}', url: '${gitRepoUrl}'
            }
        }
        stage('Run Build Script') {
            steps {
                sh './build.sh'
            }
        }
    }
}
"""

// Set the pipeline definition
job.definition = new org.jenkinsci.plugins.workflow.cps.CpsFlowDefinition(pipelineScript, true)
job.save()

println "Job ${jobName} created successfully!"
