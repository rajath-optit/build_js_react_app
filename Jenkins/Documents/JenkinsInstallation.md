**Jenkins Installation:**

Please refer this for Jenkins installation from official document 
https://www.jenkins.io/doc/book/installing/

**Installation of java:**
You can choose the java version based upon your Jenkins version requirement. 
- **Java 8:**   apt-get install -y openjdk-8-jdk
- **Java 11:**  apt-get install openjdk-11-jdk
- **Java 17:**  apt install openjdk-17-jdk **or** apt install openjdk-17-jre
  
To verify the java version currently installed in your VM through the command “**java --version**” 
![image](https://github.com/optimize-it/CICD/assets/168013366/45e7b76f-3fb6-4e85-8312-c51e0c9bf42c)

Do you want to change the java version in your Ubuntu VM? Use the following command to change the OpenJDK version.
-	sudo update-alternatives --config java

**Verify the following documentation for supported java version for your Jenkins version:**
https://www.jenkins.io/doc/book/platform-information/support-policy-java/
 
**Step 1: Installation of Jenkins**

**Note:** Follow this official documentation steps to install the Jenkins 
https://www.jenkins.io/doc/book/installing/

**Step 2:  Starting the Jenkins**

Now that Jenkins is installed, start it by using systemctl
-	sudo systemctl start Jenkins
You can use the status command to verify that Jenkins started successfully:
-	sudo systemctl status jenkins
If everything went well, the beginning of the status output shows that the service is active and configured to start at boot:
 ![image](https://github.com/optimize-it/CICD/assets/168013366/0ac335fb-7458-4944-bd89-43d7df973f0c)

**Step 3: Setting Up Jenkins**

To set up your installation, visit Jenkins on its port 8080, using your server domain name or IP address: http://localhost:8080
You should receive the Unlock Jenkins screen, which displays the location of the initial password:
 ![image](https://github.com/optimize-it/CICD/assets/168013366/87f37512-ed0c-47b5-9c07-d1d9d458a489)

Use the below command to get the 32-character initial admin password
·**	cat /var/lib/jenkins/secrets/initialAdminPassword**
Next, the option of installing suggested plugins or selecting specific plugins:

-	**Install suggested plugins** - to install the recommended set of plugins, which are based on most common use cases.
-	**Select plugins to install** - to choose which set of plugins to initially install. When you first access the plugin selection page, the suggested plugins are selected by default.
 ![image](https://github.com/optimize-it/CICD/assets/168013366/801057ea-70f3-4cb1-8600-78791a8f3e83)

When the installation is complete, you will prompted to set up the first administrative user. It’s possible to skip this step and continue as admin using the initial password from above, but we’ll take a moment to create the user.
 ![image](https://github.com/optimize-it/CICD/assets/168013366/91cada49-50ad-4b2c-9da0-9b0f9c6e38e5)

You will receive an Instance Configuration page that will ask you to confirm the preferred URL for your Jenkins instance. Confirm either the domain name for your server or your server’s IP address like http://localhost:8080
After confirming the appropriate information, click Save and Finish. You will receive a confirmation page confirming the “**Jenkins is Ready!**”
 ![image](https://github.com/optimize-it/CICD/assets/168013366/20fb2734-2357-433b-a2a6-0a337372c039)

**Step 4: Installation of Plugins**

- Plugins are the primary means of enhancing the functionality of a Jenkins environment to suit organization- or user-specific needs.
- In **Manage Jenkins**, under “**system configuration**” there is the “**plugins**” section which helps to add, remove or upgrade plugins that can extend the functionality of Jenkins.
  ![image](https://github.com/optimize-it/CICD/assets/168013366/f8760cd9-dbe3-4920-9ce1-1aa251b5ca69)

**Step 5: Defining and Using Shared Libraries**

-	In Manage Jenkins, Click on “**Configure System**” under “**System Configuration**” towards the top.
-	Under “**Global Pipeline Libraries**” enter the appropriate name which will be used to identify the library for importing the shared library from GitHub.
 ![image](https://github.com/optimize-it/CICD/assets/168013366/d26c7cec-4d09-4331-8900-9f58b5cdf12f)

 


