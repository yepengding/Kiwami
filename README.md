# Kiwami

A generic bounded model checker.

## Feature

- [x] Structure checking
- [x] Termination / Maximal step calculation
- [x] In-memory structure construction
- [x] On-disk structure construction
- [ ] Structure minimization
- [x] Encode Kripke structure
- [ ] Generate SMT-LIB programs
- [ ] LTL model checking
- [ ] CTL model checking
- [ ] Modeling language

## Build

### Environment

- Java 17
- Maven 3.6+

### Maven

```shell
mvn clean compile assembly:single
```

---

# References

- Biere, A., Cimatti, A., Clarke, E. M., Strichman, O., & Zhu, Y. (2003). Bounded model checking.
- Clarke, E., Biere, A., Raimi, R., & Zhu, Y. (2001). Bounded model checking using satisfiability solving. Formal
  methods in system design, 19(1), 7-34.