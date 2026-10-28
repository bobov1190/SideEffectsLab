# SideEffectsLab

Practice work "Side-effects" (Jetpack Compose effects).

## Theory

`theory.txt` contains the answers to the theoretical questions (Part A).

## Practice

### Task 1. Splash screen

API used: `LaunchedEffect` + `rememberUpdatedState`.

- `LaunchedEffect(true)` waits 2 seconds, because the wait must start once
  when the screen appears and must not restart.
- `rememberUpdatedState` gives the effect the latest `onTimeout` lambda
  without restarting it. The variant that captures the lambda directly
  calls the old one (v0), the variant with `rememberUpdatedState` calls
  the newest one.
- `LaunchedEffect(onTimeout)` would be wrong: the parent creates a new
  lambda every 500 ms, so the wait would restart all the time.
