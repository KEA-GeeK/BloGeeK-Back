# BloGeeK-Back
BloGeeK의 Backend 레포지터리입니다

```
src
├─ main
│  ├─ java
│  │  └─ com
│  │     └─ example
│  │        └─ BlogPost
│  │           ├─ BlogPostApplication.java
│  │           ├─ controller
│  │           │  ├─ HomeController.java
│  │           │  ├─ PostController.java
│  │           │  └─ PostForm.java
│  │           ├─ entity
│  │           │  └─ Post.java
│  │           ├─ repository
│  │           │  ├─ JDBCTemplatePostRepository.java
│  │           │  ├─ JPAPostRepository.java
│  │           │  └─ PostRepository.java
│  │           ├─ service
│  │           │  └─ PostService.java
│  │           └─ SpringConfig.java
│  └─ resources
│     ├─ application.properties
│     ├─ static
│     └─ templates
│        ├─ home.html
│        └─ post
│           ├─ viewAllPosts.html
│           ├─ viewPost.html
│           └─ writePost.html
└─ test
  └─ java
     └─ com
        └─ example
           └─ BlogPost
              ├─ BlogUserApplicationTests.java
              ├─ repository
              │  ├─ DatabaseConnectionTest.java
              │  └─ JDBCTemplatePostRepositoryTest.java
              └─ service
                 └─ PostServiceIntegrationTest.java

```