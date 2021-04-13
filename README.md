<!-- PROJECT LOGO -->
<h2 align="center">A Visual Improvement to the Pedagogy of Introductory Logic</h2>

<p align="center">
    <img src="/docs/images/llatlogo.png" alt="LLAT Logo">
</p>

<p align="justify">
    <strong>Abstract:</strong> Formal logic is considered by many students to be challenging to overcome, exposing students to confusing symbols, rules, axioms, and other concepts that look similar to concepts from discrete mathematics and computer science. Figures or images frequently accompany textbooks and other sources when the need to demonstrate a problem or example arises. However, these often leave a lot to be desired - some concepts come easier than others, such as truth tables versus proof-based natural deduction, and it largely depends on the student/user. Certain programming languages and websites exist that serve similar purposes but generally do not provide user-friendly solutions to non-programmers and those that are not already experts at the material. Our current work is focused on building a visually appealing aid and tool to complement the traditional textbook and lecture pedagogy. It provides beginner to intermediate students with a digital canvas to explore formal logic definitions, rules, and tools at their own pace in attempts to improve their overall understanding of the material.
</p>

[![CodeFactor](https://www.codefactor.io/repository/github/joshuacrotts/Logic-Learning-Assistance-Tool/badge)](https://www.codefactor.io/repository/github/joshuacrotts/Logic-Learning-Assistance-Tool) ![GitHub contributors](https://img.shields.io/github/contributors/JoshuaCrotts/Logic-Learning-Assistance-Tool) ![GitHub commit activity](https://img.shields.io/github/commit-activity/m/JoshuaCrotts/Logic-Learning-Assistance-Tool) ![GitHub repo size](https://img.shields.io/github/repo-size/JoshuaCrotts/Logic-Learning-Assistance-Tool) [![GitHub issues open](https://img.shields.io/github/issues/JoshuaCrotts/Logic-Learning-Assistance-Tool)]()
[![GitHub issues closed](https://img.shields.io/github/issues-closed-raw/JoshuaCrotts/Logic-Learning-Assistance-Tool)]()

## Logic-Learning Assistance Tool
LLAT (pronounced L-LÆT) allows students to build truth trees, parse trees, and truth tables. In addition, there are several algorithms to choose from which examine the well-formed formula entered. These include detecting the main operator, bound/free variable detector, argument validity determiner, and many more.

## Dependencies
The following is a list of dependencies used in this project. As described in the next section, we use Maven as our build automation tool, so it isn't necessary to hunt these down yourself.
1. BCrypt
2. JDBC (Java Database Connector)
3. JavaFX
4. Abego Tree Library
5. ANTLR4
6. Java 14/15

## Rebuilding LLAT
To rebuild the code, clone the repository to your computer. This project is setup to use Maven, so all dependencies should work natively in your IDE. We developed the application with IntelliJ and OpenJDK15. Compile via `make clean compile install` and be sure to refresh your Maven sources. Then, run `App.java`.

## Developers 📣

- [**Ali Altamimi**](https://github.com/CodingTheories)
- [**Harinderveer Badesha**](https://github.com/HarinB4)
- [**Christopher Brantley**](https://github.com/ccbrantley)
- [**Joshua Crotts**](https://github.com/JoshuaCrotts)
- [**Nadia Doudou**](https://github.com/diatt17)
